/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.manytomany.web;

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
import com.jeeplus.modules.test.manytomany.entity.Course;
import com.jeeplus.modules.test.manytomany.service.CourseService;

/**
 * 课程Controller
 * @author lgf
 * @version 2018-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/test/manytomany/course")
public class CourseController extends BaseController {

	@Autowired
	private CourseService courseService;
	
	@ModelAttribute
	public Course get(@RequestParam(required=false) String id) {
		Course entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = courseService.get(id);
		}
		if (entity == null){
			entity = new Course();
		}
		return entity;
	}
	
	/**
	 * 课程列表页面
	 */
	@RequiresPermissions("test:manytomany:course:list")
	@RequestMapping(value = {"list", ""})
	public String list(Course course, Model model) {
		model.addAttribute("course", course);
		return "modules/test/manytomany/courseList";
	}
	
		/**
	 * 课程列表数据
	 */
	@ResponseBody
	@RequiresPermissions("test:manytomany:course:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Course course, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Course> page = courseService.findPage(new Page<Course>(request, response), course); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑课程表单页面
	 */
	@RequiresPermissions(value={"test:manytomany:course:view","test:manytomany:course:add","test:manytomany:course:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Course course, Model model) {
		model.addAttribute("course", course);
		model.addAttribute("mode", mode);
		return "modules/test/manytomany/courseForm";
	}

	/**
	 * 保存课程
	 */
	@ResponseBody
	@RequiresPermissions(value={"test:manytomany:course:add","test:manytomany:course:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Course course, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(course);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		courseService.save(course);//保存
		j.setSuccess(true);
		j.setMsg("保存课程成功");
		return j;
	}
	
	/**
	 * 删除课程
	 */
	@ResponseBody
	@RequiresPermissions("test:manytomany:course:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Course course) {
		AjaxJson j = new AjaxJson();
		courseService.delete(course);
		j.setMsg("删除课程成功");
		return j;
	}
	
	/**
	 * 批量删除课程
	 */
	@ResponseBody
	@RequiresPermissions("test:manytomany:course:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			courseService.delete(courseService.get(id));
		}
		j.setMsg("删除课程成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("test:manytomany:course:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Course course, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "课程"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Course> page = courseService.findPage(new Page<Course>(request, response, -1), course);
    		new ExportExcel("课程", Course.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出课程记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("test:manytomany:course:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Course> list = ei.getDataList(Course.class);
			for (Course course : list){
				try{
					courseService.save(course);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条课程记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条课程记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入课程失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入课程数据模板
	 */
	@ResponseBody
	@RequiresPermissions("test:manytomany:course:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "课程数据导入模板.xlsx";
    		List<Course> list = Lists.newArrayList(); 
    		new ExportExcel("课程数据", Course.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}