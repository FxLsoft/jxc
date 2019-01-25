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
import com.jeeplus.modules.jxc.entity.RevenueOrder;
import com.jeeplus.modules.jxc.service.RevenueOrderService;

/**
 * 应收单Controller
 * @author 周涛
 * @version 2018-12-23
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/revenueOrder")
public class RevenueOrderController extends BaseController {

	@Autowired
	private RevenueOrderService revenueOrderService;
	
	@ModelAttribute
	public RevenueOrder get(@RequestParam(required=false) String id) {
		RevenueOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = revenueOrderService.get(id);
		}
		if (entity == null){
			entity = new RevenueOrder();
		}
		return entity;
	}
	
	/**
	 * 应收单信息列表页面
	 */
	@RequiresPermissions("jxc:revenueOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list(RevenueOrder revenueOrder, Model model) {
		model.addAttribute("revenueOrder", revenueOrder);
		return "modules/jxc/revenueOrderList";
	}
	
		/**
	 * 应收单信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:revenueOrder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(RevenueOrder revenueOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RevenueOrder> page = revenueOrderService.findPage(new Page<RevenueOrder>(request, response), revenueOrder); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑应收单信息表单页面
	 */
	@RequiresPermissions(value={"jxc:revenueOrder:view","jxc:revenueOrder:add","jxc:revenueOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, RevenueOrder revenueOrder, Model model) {
		if ("add".equals(mode)) {
			revenueOrder.setNo("YS" + IdWorker.getId());
		}
		model.addAttribute("revenueOrder", revenueOrder);
		model.addAttribute("mode", mode);
		return "modules/jxc/revenueOrderForm";
	}

	/**
	 * 保存应收单信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:revenueOrder:add","jxc:revenueOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(RevenueOrder revenueOrder, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(revenueOrder);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		if (revenueOrder.getStatus() == null) {
			revenueOrder.setStatus(0);
		}
		if (revenueOrder.getStatus() == 1) {
			j.setSuccess(false);
			j.setMsg("已提交单不能被修改！");
			return j;
		}
		revenueOrderService.save(revenueOrder);//保存
		j.setSuccess(true);
		j.setMsg("保存应收单信息成功");
		return j;
	}
	
	/**
	 * 删除应收单信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:revenueOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(RevenueOrder revenueOrder) {
		AjaxJson j = new AjaxJson();
		revenueOrderService.delete(revenueOrder);
		j.setMsg("删除应收单信息成功");
		return j;
	}
	
	/**
	 * 批量删除应收单信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:revenueOrder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			revenueOrderService.delete(revenueOrderService.get(id));
		}
		j.setMsg("删除应收单信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:revenueOrder:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(RevenueOrder revenueOrder, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "应收单信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<RevenueOrder> page = revenueOrderService.findPage(new Page<RevenueOrder>(request, response, -1), revenueOrder);
    		new ExportExcel("应收单信息", RevenueOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出应收单信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:revenueOrder:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<RevenueOrder> list = ei.getDataList(RevenueOrder.class);
			for (RevenueOrder revenueOrder : list){
				try{
					revenueOrderService.save(revenueOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条应收单信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条应收单信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入应收单信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入应收单信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:revenueOrder:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "应收单信息数据导入模板.xlsx";
    		List<RevenueOrder> list = Lists.newArrayList(); 
    		new ExportExcel("应收单信息数据", RevenueOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}