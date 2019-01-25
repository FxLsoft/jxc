/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.treetable.form.web;

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
import com.jeeplus.modules.test.treetable.form.entity.Car2;
import com.jeeplus.modules.test.treetable.form.service.Car2Service;

/**
 * 车辆Controller
 * @author lgf
 * @version 2018-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/test/treetable/form/car2")
public class Car2Controller extends BaseController {

	@Autowired
	private Car2Service car2Service;
	
	@ModelAttribute
	public Car2 get(@RequestParam(required=false) String id) {
		Car2 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = car2Service.get(id);
		}
		if (entity == null){
			entity = new Car2();
		}
		return entity;
	}
	
	/**
	 * 车辆列表页面
	 */
	@RequiresPermissions("test:treetable:form:car2:list")
	@RequestMapping(value = {"list", ""})
	public String list(Car2 car2, Model model) {
		model.addAttribute("car2", car2);
		return "modules/test/treetable/form/car2List";
	}
	
		/**
	 * 车辆列表数据
	 */
	@ResponseBody
	@RequiresPermissions("test:treetable:form:car2:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Car2 car2, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Car2> page = car2Service.findPage(new Page<Car2>(request, response), car2); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑车辆表单页面
	 */
	@RequiresPermissions(value={"test:treetable:form:car2:view","test:treetable:form:car2:add","test:treetable:form:car2:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Car2 car2, Model model) {
		model.addAttribute("car2", car2);
		model.addAttribute("mode", mode);
		return "modules/test/treetable/form/car2Form";
	}

	/**
	 * 保存车辆
	 */
	@ResponseBody
	@RequiresPermissions(value={"test:treetable:form:car2:add","test:treetable:form:car2:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Car2 car2, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(car2);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		car2Service.save(car2);//保存
		j.setSuccess(true);
		j.setMsg("保存车辆成功");
		return j;
	}
	
	/**
	 * 删除车辆
	 */
	@ResponseBody
	@RequiresPermissions("test:treetable:form:car2:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Car2 car2) {
		AjaxJson j = new AjaxJson();
		car2Service.delete(car2);
		j.setMsg("删除车辆成功");
		return j;
	}
	
	/**
	 * 批量删除车辆
	 */
	@ResponseBody
	@RequiresPermissions("test:treetable:form:car2:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			car2Service.delete(car2Service.get(id));
		}
		j.setMsg("删除车辆成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("test:treetable:form:car2:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Car2 car2, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "车辆"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Car2> page = car2Service.findPage(new Page<Car2>(request, response, -1), car2);
    		new ExportExcel("车辆", Car2.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出车辆记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("test:treetable:form:car2:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Car2> list = ei.getDataList(Car2.class);
			for (Car2 car2 : list){
				try{
					car2Service.save(car2);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条车辆记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条车辆记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入车辆失败！失败信息："+e.getMessage());
		}
		return j;
    }

	/**
	 * 下载导入车辆数据模板
	 */
	@ResponseBody
	@RequiresPermissions("test:treetable:form:car2:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "车辆数据导入模板.xlsx";
    		List<Car2> list = Lists.newArrayList();
    		new ExportExcel("车辆数据", Car2.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }


}