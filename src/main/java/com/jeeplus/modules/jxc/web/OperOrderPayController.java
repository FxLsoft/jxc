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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.jxc.entity.OperOrderPay;
import com.jeeplus.modules.jxc.service.OperOrderPayService;

/**
 * 财务记录Controller
 * @author FxLsoft
 * @version 2019-02-16
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/operOrderPay")
public class OperOrderPayController extends BaseController {

	@Autowired
	private OperOrderPayService operOrderPayService;
	
	@ModelAttribute
	public OperOrderPay get(@RequestParam(required=false) String id) {
		OperOrderPay entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = operOrderPayService.get(id);
		}
		if (entity == null){
			entity = new OperOrderPay();
		}
		return entity;
	}
	
	/**
	 * 财务信息列表页面
	 */
	@RequiresPermissions("jxc:operOrderPay:list")
	@RequestMapping(value = {"list", ""})
	public String list(OperOrderPay operOrderPay, Model model) {
		model.addAttribute("operOrderPay", operOrderPay);
		return "modules/jxc/operOrderPayList";
	}
	
		/**
	 * 财务信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:operOrderPay:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OperOrderPay operOrderPay, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OperOrderPay> page = operOrderPayService.findPage(new Page<OperOrderPay>(request, response), operOrderPay); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑财务信息表单页面
	 */
	@RequiresPermissions(value={"jxc:operOrderPay:view","jxc:operOrderPay:add","jxc:operOrderPay:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, OperOrderPay operOrderPay, Model model) {
		model.addAttribute("operOrderPay", operOrderPay);
		model.addAttribute("mode", mode);
		return "modules/jxc/operOrderPayForm";
	}

	/**
	 * 保存财务信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:operOrderPay:add","jxc:operOrderPay:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(OperOrderPay operOrderPay, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(operOrderPay);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		operOrderPayService.save(operOrderPay);//保存
		j.setSuccess(true);
		j.setMsg("保存财务信息成功");
		return j;
	}
	
	/**
	 * 删除财务信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:operOrderPay:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OperOrderPay operOrderPay) {
		AjaxJson j = new AjaxJson();
		operOrderPayService.delete(operOrderPay);
		j.setMsg("删除财务信息成功");
		return j;
	}
	
	/**
	 * 批量删除财务信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:operOrderPay:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			operOrderPayService.delete(operOrderPayService.get(id));
		}
		j.setMsg("删除财务信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:operOrderPay:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(OperOrderPay operOrderPay, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "财务信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OperOrderPay> page = operOrderPayService.findPage(new Page<OperOrderPay>(request, response, -1), operOrderPay);
    		new ExportExcel("财务信息", OperOrderPay.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出财务信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:operOrderPay:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OperOrderPay> list = ei.getDataList(OperOrderPay.class);
			for (OperOrderPay operOrderPay : list){
				try{
					operOrderPayService.save(operOrderPay);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条财务信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条财务信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入财务信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入财务信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:operOrderPay:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "财务信息数据导入模板.xlsx";
    		List<OperOrderPay> list = Lists.newArrayList(); 
    		new ExportExcel("财务信息数据", OperOrderPay.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}