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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.jxc.entity.Employee;
import com.jeeplus.modules.jxc.service.EmployeeService;

/**
 * 员工信息Controller
 * @author FxLsoft
 * @version 2018-12-22
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/employee")
public class EmployeeController extends BaseController {

	@Autowired
	private EmployeeService employeeService;
	
	@ModelAttribute
	public Employee get(@RequestParam(required=false) String id) {
		Employee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = employeeService.get(id);
		}
		if (entity == null){
			entity = new Employee();
		}
		return entity;
	}
	
	/**
	 * 员工信息列表页面
	 */
	@RequiresPermissions("jxc:employee:list")
	@RequestMapping(value = {"list", ""})
	public String list(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return "modules/jxc/employeeList";
	}
	
		/**
	 * 员工信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:employee:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Employee employee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Employee> page = employeeService.findPage(new Page<Employee>(request, response), employee); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑员工信息表单页面
	 */
	@RequiresPermissions(value={"jxc:employee:view","jxc:employee:add","jxc:employee:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return "modules/jxc/employeeForm";
	}

	/**
	 * 保存员工信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:employee:add","jxc:employee:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Employee employee, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(employee);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		employeeService.save(employee);//保存
		j.setSuccess(true);
		j.setMsg("保存员工信息成功");
		return j;
	}
	
	/**
	 * 删除员工信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:employee:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Employee employee) {
		AjaxJson j = new AjaxJson();
		employeeService.delete(employee);
		j.setMsg("删除员工信息成功");
		return j;
	}
	
	/**
	 * 批量删除员工信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:employee:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			employeeService.delete(employeeService.get(id));
		}
		j.setMsg("删除员工信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:employee:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Employee employee, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Employee> page = employeeService.findPage(new Page<Employee>(request, response, -1), employee);
    		new ExportExcel("员工信息", Employee.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出员工信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:employee:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Employee> list = ei.getDataList(Employee.class);
			for (Employee employee : list){
				try{
					employeeService.save(employee);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条员工信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条员工信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入员工信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入员工信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:employee:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工信息数据导入模板.xlsx";
    		List<Employee> list = Lists.newArrayList(); 
    		new ExportExcel("员工信息数据", Employee.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}