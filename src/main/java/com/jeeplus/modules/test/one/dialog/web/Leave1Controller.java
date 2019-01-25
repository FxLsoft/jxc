/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.one.dialog.web;

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
import com.jeeplus.modules.test.one.dialog.entity.Leave1;
import com.jeeplus.modules.test.one.dialog.service.Leave1Service;

/**
 * 请假表单Controller
 * @author lgf
 * @version 2018-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/test/one/dialog/leave1")
public class Leave1Controller extends BaseController {

	@Autowired
	private Leave1Service leave1Service;
	
	@ModelAttribute
	public Leave1 get(@RequestParam(required=false) String id) {
		Leave1 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leave1Service.get(id);
		}
		if (entity == null){
			entity = new Leave1();
		}
		return entity;
	}
	
	/**
	 * 请假表单列表页面
	 */
	@RequiresPermissions("test:one:dialog:leave1:list")
	@RequestMapping(value = {"list", ""})
	public String list(Leave1 leave1, Model model) {
		model.addAttribute("leave1", leave1);
		return "modules/test/one/dialog/leave1List";
	}
	
		/**
	 * 请假表单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("test:one:dialog:leave1:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Leave1 leave1, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Leave1> page = leave1Service.findPage(new Page<Leave1>(request, response), leave1); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑请假表单表单页面
	 */
	@RequiresPermissions(value={"test:one:dialog:leave1:view","test:one:dialog:leave1:add","test:one:dialog:leave1:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Leave1 leave1, Model model) {
		model.addAttribute("leave1", leave1);
		return "modules/test/one/dialog/leave1Form";
	}

	/**
	 * 保存请假表单
	 */
	@ResponseBody
	@RequiresPermissions(value={"test:one:dialog:leave1:add","test:one:dialog:leave1:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Leave1 leave1, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(leave1);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		leave1Service.save(leave1);//保存
		j.setSuccess(true);
		j.setMsg("保存请假表单成功");
		return j;
	}
	
	/**
	 * 删除请假表单
	 */
	@ResponseBody
	@RequiresPermissions("test:one:dialog:leave1:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Leave1 leave1) {
		AjaxJson j = new AjaxJson();
		leave1Service.delete(leave1);
		j.setMsg("删除请假表单成功");
		return j;
	}
	
	/**
	 * 批量删除请假表单
	 */
	@ResponseBody
	@RequiresPermissions("test:one:dialog:leave1:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			leave1Service.delete(leave1Service.get(id));
		}
		j.setMsg("删除请假表单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("test:one:dialog:leave1:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Leave1 leave1, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "请假表单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Leave1> page = leave1Service.findPage(new Page<Leave1>(request, response, -1), leave1);
    		new ExportExcel("请假表单", Leave1.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("test:one:dialog:leave1:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Leave1> list = ei.getDataList(Leave1.class);
			for (Leave1 leave1 : list){
				try{
					leave1Service.save(leave1);
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
	@RequiresPermissions("test:one:dialog:leave1:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "请假表单数据导入模板.xlsx";
    		List<Leave1> list = Lists.newArrayList(); 
    		new ExportExcel("请假表单数据", Leave1.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}