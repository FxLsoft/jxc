/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.one.form.web;

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
import com.jeeplus.modules.test.one.form.entity.Leave2;
import com.jeeplus.modules.test.one.form.service.Leave2Service;

/**
 * 请假表单Controller
 * @author lgf
 * @version 2018-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/test/one/form/leave2")
public class Leave2Controller extends BaseController {

	@Autowired
	private Leave2Service leave2Service;
	
	@ModelAttribute
	public Leave2 get(@RequestParam(required=false) String id) {
		Leave2 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leave2Service.get(id);
		}
		if (entity == null){
			entity = new Leave2();
		}
		return entity;
	}
	
	/**
	 * 请假表单列表页面
	 */
	@RequiresPermissions("test:one:form:leave2:list")
	@RequestMapping(value = {"list", ""})
	public String list(Leave2 leave2, Model model) {
		model.addAttribute("leave2", leave2);
		return "modules/test/one/form/leave2List";
	}
	
		/**
	 * 请假表单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("test:one:form:leave2:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Leave2 leave2, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Leave2> page = leave2Service.findPage(new Page<Leave2>(request, response), leave2); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑请假表单表单页面
	 */
	@RequiresPermissions(value={"test:one:form:leave2:view","test:one:form:leave2:add","test:one:form:leave2:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Leave2 leave2, Model model) {
		model.addAttribute("leave2", leave2);
		model.addAttribute("mode", mode);
		return "modules/test/one/form/leave2Form";
	}

	/**
	 * 保存请假表单
	 */
	@ResponseBody
	@RequiresPermissions(value={"test:one:form:leave2:add","test:one:form:leave2:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Leave2 leave2, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(leave2);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		leave2Service.save(leave2);//保存
		j.setSuccess(true);
		j.setMsg("保存请假表单成功");
		return j;
	}
	
	/**
	 * 删除请假表单
	 */
	@ResponseBody
	@RequiresPermissions("test:one:form:leave2:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Leave2 leave2) {
		AjaxJson j = new AjaxJson();
		leave2Service.delete(leave2);
		j.setMsg("删除请假表单成功");
		return j;
	}
	
	/**
	 * 批量删除请假表单
	 */
	@ResponseBody
	@RequiresPermissions("test:one:form:leave2:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			leave2Service.delete(leave2Service.get(id));
		}
		j.setMsg("删除请假表单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("test:one:form:leave2:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Leave2 leave2, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "请假表单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Leave2> page = leave2Service.findPage(new Page<Leave2>(request, response, -1), leave2);
    		new ExportExcel("请假表单", Leave2.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出请假表单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("test:one:form:leave2:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Leave2> list = ei.getDataList(Leave2.class);
			for (Leave2 leave2 : list){
				try{
					leave2Service.save(leave2);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条请假表单记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条请假表单记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入请假表单失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入请假表单数据模板
	 */
	@ResponseBody
	@RequiresPermissions("test:one:form:leave2:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "请假表单数据导入模板.xlsx";
    		List<Leave2> list = Lists.newArrayList(); 
    		new ExportExcel("请假表单数据", Leave2.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}