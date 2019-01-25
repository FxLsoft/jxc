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
import com.jeeplus.modules.jxc.entity.PurchaseOrder;
import com.jeeplus.modules.jxc.service.PurchaseOrderService;

/**
 * 采购订单Controller
 * @author FxLsoft
 * @version 2018-12-22
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/purchaseOrder")
public class PurchaseOrderController extends BaseController {

	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	
	
	@ModelAttribute
	public PurchaseOrder get(@RequestParam(required=false) String id) {
		PurchaseOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = purchaseOrderService.get(id);
		}
		if (entity == null){
			entity = new PurchaseOrder();
		}
		return entity;
	}
	
	/**
	 * 采购订单列表页面
	 */
	@RequiresPermissions("jxc:purchaseOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list(PurchaseOrder purchaseOrder, Model model) {
		model.addAttribute("purchaseOrder", purchaseOrder);
		return "modules/jxc/purchaseOrderList";
	}
	
		/**
	 * 采购订单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:purchaseOrder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(PurchaseOrder purchaseOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaseOrder> page = purchaseOrderService.findPage(new Page<PurchaseOrder>(request, response), purchaseOrder); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑采购订单表单页面
	 */
	@RequiresPermissions(value={"jxc:purchaseOrder:view","jxc:purchaseOrder:add","jxc:purchaseOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, PurchaseOrder purchaseOrder, Model model) {
		if ("add".equals(mode)) {
			purchaseOrder.setNo("CG" + IdWorker.getId());
		}
		model.addAttribute("purchaseOrder", purchaseOrder);
		model.addAttribute("mode", mode);
		return "modules/jxc/purchaseOrderForm";
	}

	/**
	 * 保存采购订单
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:purchaseOrder:add","jxc:purchaseOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(PurchaseOrder purchaseOrder, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(purchaseOrder);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		if (purchaseOrder.getStatus() == null) {
			purchaseOrder.setStatus(0);
		}
		if (purchaseOrder.getStatus() == 1) {
			j.setSuccess(false);
			j.setMsg("已提交单不能被修改！");
			return j;
		}
		purchaseOrderService.save(purchaseOrder);//保存
		j.setSuccess(true);
		j.setMsg("保存采购订单成功");
		return j;
	}
	
	/**
	 * 删除采购订单
	 */
	@ResponseBody
	@RequiresPermissions("jxc:purchaseOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(PurchaseOrder purchaseOrder) {
		AjaxJson j = new AjaxJson();
		purchaseOrderService.delete(purchaseOrder);
		j.setMsg("删除采购订单成功");
		return j;
	}
	
	/**
	 * 批量删除采购订单
	 */
	@ResponseBody
	@RequiresPermissions("jxc:purchaseOrder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			purchaseOrderService.delete(purchaseOrderService.get(id));
		}
		j.setMsg("删除采购订单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:purchaseOrder:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(PurchaseOrder purchaseOrder, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "采购订单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PurchaseOrder> page = purchaseOrderService.findPage(new Page<PurchaseOrder>(request, response, -1), purchaseOrder);
    		new ExportExcel("采购订单", PurchaseOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出采购订单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public PurchaseOrder detail(String id) {
		return purchaseOrderService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:purchaseOrder:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PurchaseOrder> list = ei.getDataList(PurchaseOrder.class);
			for (PurchaseOrder purchaseOrder : list){
				try{
					purchaseOrderService.save(purchaseOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条采购订单记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条采购订单记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入采购订单失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入采购订单数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:purchaseOrder:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "采购订单数据导入模板.xlsx";
    		List<PurchaseOrder> list = Lists.newArrayList(); 
    		new ExportExcel("采购订单数据", PurchaseOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}