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
import com.jeeplus.modules.jxc.entity.SaleOrder;
import com.jeeplus.modules.jxc.service.SaleOrderService;

/**
 * 销售单Controller
 * @author 周涛
 * @version 2018-12-24
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/saleOrder")
public class SaleOrderController extends BaseController {

	@Autowired
	private SaleOrderService saleOrderService;
	
	@ModelAttribute
	public SaleOrder get(@RequestParam(required=false) String id) {
		SaleOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = saleOrderService.get(id);
		}
		if (entity == null){
			entity = new SaleOrder();
		}
		return entity;
	}
	
	/**
	 * 销售单信息列表页面
	 */
	@RequiresPermissions("jxc:saleOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list(SaleOrder saleOrder, Model model) {
		model.addAttribute("saleOrder", saleOrder);
		return "modules/jxc/saleOrderList";
	}
	
		/**
	 * 销售单信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:saleOrder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SaleOrder saleOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SaleOrder> page = saleOrderService.findPage(new Page<SaleOrder>(request, response), saleOrder); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑销售单信息表单页面
	 */
	@RequiresPermissions(value={"jxc:saleOrder:view","jxc:saleOrder:add","jxc:saleOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, SaleOrder saleOrder, Model model) {
		if ("add".equals(mode)) {
			saleOrder.setNo("XS" + IdWorker.getId());
			saleOrder.setType(2);
		}
		model.addAttribute("saleOrder", saleOrder);
		model.addAttribute("mode", mode);
		return "modules/jxc/saleOrderForm";
	}

	/**
	 * 保存销售单信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:saleOrder:add","jxc:saleOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(SaleOrder saleOrder, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(saleOrder);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		if (saleOrder.getStatus() == null) {
			saleOrder.setStatus(0);
		}
		if (saleOrder.getStatus() == 1) {
			j.setSuccess(false);
			j.setMsg("已提交单不能被修改！");
			return j;
		}
		saleOrderService.save(saleOrder);//保存
		j.setSuccess(true);
		j.setMsg("保存销售单信息成功");
		return j;
	}
	
	/**
	 * 删除销售单信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:saleOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(SaleOrder saleOrder) {
		AjaxJson j = new AjaxJson();
		saleOrderService.delete(saleOrder);
		j.setMsg("删除销售单信息成功");
		return j;
	}
	
	/**
	 * 批量删除销售单信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:saleOrder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			saleOrderService.delete(saleOrderService.get(id));
		}
		j.setMsg("删除销售单信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:saleOrder:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(SaleOrder saleOrder, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "销售单信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SaleOrder> page = saleOrderService.findPage(new Page<SaleOrder>(request, response, -1), saleOrder);
    		new ExportExcel("销售单信息", SaleOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出销售单信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public SaleOrder detail(String id) {
		return saleOrderService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:saleOrder:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SaleOrder> list = ei.getDataList(SaleOrder.class);
			for (SaleOrder saleOrder : list){
				try{
					saleOrderService.save(saleOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条销售单信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条销售单信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入销售单信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入销售单信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:saleOrder:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "销售单信息数据导入模板.xlsx";
    		List<SaleOrder> list = Lists.newArrayList(); 
    		new ExportExcel("销售单信息数据", SaleOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}