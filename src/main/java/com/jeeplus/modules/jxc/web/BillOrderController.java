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
import com.jeeplus.modules.jxc.entity.BillOrder;
import com.jeeplus.modules.jxc.service.BillOrderService;

/**
 * 应付单Controller
 * @author 周涛
 * @version 2018-12-22
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/billOrder")
public class BillOrderController extends BaseController {

	@Autowired
	private BillOrderService billOrderService;
	
	@ModelAttribute
	public BillOrder get(@RequestParam(required=false) String id) {
		BillOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = billOrderService.get(id);
		}
		if (entity == null){
			entity = new BillOrder();
		}
		return entity;
	}
	
	/**
	 * 应付单信息列表页面
	 */
	@RequiresPermissions("jxc:billOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list(BillOrder billOrder, Model model) {
		model.addAttribute("billOrder", billOrder);
		return "modules/jxc/billOrderList";
	}
	
		/**
	 * 应付单信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:billOrder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BillOrder billOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BillOrder> page = billOrderService.findPage(new Page<BillOrder>(request, response), billOrder); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑应付单信息表单页面
	 */
	@RequiresPermissions(value={"jxc:billOrder:view","jxc:billOrder:add","jxc:billOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BillOrder billOrder, Model model) {
		if ("add".equals(mode)) {
			billOrder.setNo("SJ" + IdWorker.getId());
		}
		model.addAttribute("billOrder", billOrder);
		model.addAttribute("mode", mode);
		return "modules/jxc/billOrderForm";
	}

	/**
	 * 保存应付单信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:billOrder:add","jxc:billOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BillOrder billOrder, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(billOrder);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		if (billOrder.getStatus() == null) {
			billOrder.setStatus(0);
		}
		if (billOrder.getStatus() == 1) {
			j.setSuccess(false);
			j.setMsg("已提交单不能被修改！");
			return j;
		}
		billOrderService.save(billOrder);//保存
		j.setSuccess(true);
		j.setMsg("保存应付单信息成功");
		return j;
	}
	
	/**
	 * 删除应付单信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:billOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(BillOrder billOrder) {
		AjaxJson j = new AjaxJson();
		billOrderService.delete(billOrder);
		j.setMsg("删除应付单信息成功");
		return j;
	}
	
	/**
	 * 批量删除应付单信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:billOrder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			billOrderService.delete(billOrderService.get(id));
		}
		j.setMsg("删除应付单信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:billOrder:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BillOrder billOrder, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "应付单信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BillOrder> page = billOrderService.findPage(new Page<BillOrder>(request, response, -1), billOrder);
    		new ExportExcel("应付单信息", BillOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出应付单信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:billOrder:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BillOrder> list = ei.getDataList(BillOrder.class);
			for (BillOrder billOrder : list){
				try{
					billOrderService.save(billOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条应付单信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条应付单信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入应付单信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入应付单信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:billOrder:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "应付单信息数据导入模板.xlsx";
    		List<BillOrder> list = Lists.newArrayList(); 
    		new ExportExcel("应付单信息数据", BillOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}