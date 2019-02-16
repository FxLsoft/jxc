/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdWorker;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.jxc.entity.OperOrder;
import com.jeeplus.modules.jxc.entity.OperOrderDetail;
import com.jeeplus.modules.jxc.entity.Product;
import com.jeeplus.modules.jxc.service.OperOrderService;
import com.jeeplus.modules.jxc.service.ProductService;

/**
 * 单据Controller
 * @author FxLsoft
 * @version 2019-02-13
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/operOrder")
public class OperOrderController extends BaseController {

	@Autowired
	private OperOrderService operOrderService;
	
	@Autowired
	private ProductService productService;
	
	@ModelAttribute
	public OperOrder get(@RequestParam(required=false) String id) {
		OperOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = operOrderService.get(id);
		}
		if (entity == null){
			entity = new OperOrder();
		}
		return entity;
	}
	
	/**
	 * 单据信息列表页面
	 */
	@RequiresPermissions("jxc:operOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list(OperOrder operOrder, Model model, String from) {
		model.addAttribute("operOrder", operOrder);
		model.addAttribute("from", from);
		return "modules/jxc/operOrderList";
	}
	
		/**
	 * 单据信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:operOrder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OperOrder operOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OperOrder> page = operOrderService.findPage(new Page<OperOrder>(request, response), operOrder); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑单据信息表单页面
	 */
	@RequiresPermissions(value={"jxc:operOrder:view","jxc:operOrder:add","jxc:operOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, OperOrder operOrder, Model model, String from) {
		if ("add".equals(mode)) {
			operOrder.setNo("D" + IdWorker.getId());
			operOrder.setStatus("0");
			operOrder.setSource(from);
			// 单据来源（0：采购入库，1：盘点入库，2：退货入库，3、电子秤零售，4、零售出库，5、批发出库）
			if ("1".equals(from)) {
				// 盘点
				operOrder.setType("2");
			} else if ("0".equals(from) || "2".equals(from)) {
				// 入库
				operOrder.setType("0");
			} else {
				// 出库
				operOrder.setType("1");
			}
		}
		model.addAttribute("operOrder", operOrder);
		model.addAttribute("mode", mode);
		model.addAttribute("from", from);
		return "modules/jxc/operOrderForm";
	}

	/**
	 * 保存单据信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:operOrder:add","jxc:operOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(OperOrder operOrder, Model model, String from) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(operOrder);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		List<OperOrder> operOrderList = Lists.newArrayList();
		List<OperOrderDetail> detailList = operOrder.getOperOrderDetailList();
		for (OperOrderDetail detail: detailList) {
			Product product = productService.get(detail.getProduct().getId());
			int hasOperOrder = -1;
			for (int i = 0, size = operOrderList.size(); i < size; i++) {
				OperOrder order = operOrderList.get(i);
				if (product.getAgency().getId().equals(order.getAgency().getId())) {
					order.getOperOrderDetailList().add(detail);
					// 重新计算总金额
					order.setRealPrice(order.getRealPrice() + detail.getAmount() * detail.getOperPrice());
					order.setTotalPrice(order.getTotalPrice() + detail.getAmount() * detail.getOperPrice());
					hasOperOrder = i;
					break;
				}
			}
			if (hasOperOrder == -1) {
				OperOrder order = new OperOrder();
				List<OperOrderDetail> list = Lists.newArrayList();
				list.add(detail);
				order.setNo(operOrder.getNo());
				order.setStore(operOrder.getStore());
				order.setAgency(product.getAgency());
				order.setType(operOrder.getType());
				order.setStatus(operOrder.getStatus());
				order.setSource(operOrder.getSource());
				order.setRealPrice(detail.getAmount() * detail.getOperPrice());
				order.setTotalPrice(detail.getAmount() * detail.getOperPrice());
				order.setRealPay(0d);
				order.setRemarks(operOrder.getRemarks());
				order.setOperOrderDetailList(list);
				operOrderList.add(order);
			}
		}
		//新增或编辑表单保存
		for (OperOrder order: operOrderList) {
			operOrderService.save(order);
		}
		operOrder.setDelFlag("1");
		operOrderService.save(operOrder);
		model.addAttribute("from", from);
		j.setSuccess(true);
		j.setMsg("保存单据信息成功");
		return j;
	}
	
	/**
	 * 删除单据信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:operOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OperOrder operOrder) {
		AjaxJson j = new AjaxJson();
		operOrderService.delete(operOrder);
		j.setMsg("删除单据信息成功");
		return j;
	}
	
	/**
	 * 批量删除单据信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:operOrder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			operOrderService.delete(operOrderService.get(id));
		}
		j.setMsg("删除单据信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:operOrder:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(OperOrder operOrder, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "单据信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OperOrder> page = operOrderService.findPage(new Page<OperOrder>(request, response, -1), operOrder);
    		new ExportExcel("单据信息", OperOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出单据信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public OperOrder detail(String id) {
		return operOrderService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:operOrder:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OperOrder> list = ei.getDataList(OperOrder.class);
			for (OperOrder operOrder : list){
				try{
					operOrderService.save(operOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条单据信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条单据信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入单据信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入单据信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:operOrder:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "单据信息数据导入模板.xlsx";
    		List<OperOrder> list = Lists.newArrayList(); 
    		new ExportExcel("单据信息数据", OperOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}