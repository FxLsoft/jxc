/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.pic.web;

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
import com.jeeplus.modules.test.pic.entity.TestPic;
import com.jeeplus.modules.test.pic.service.TestPicService;

/**
 * 图片管理Controller
 * @author lgf
 * @version 2018-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/test/pic/testPic")
public class TestPicController extends BaseController {

	@Autowired
	private TestPicService testPicService;
	
	@ModelAttribute
	public TestPic get(@RequestParam(required=false) String id) {
		TestPic entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testPicService.get(id);
		}
		if (entity == null){
			entity = new TestPic();
		}
		return entity;
	}
	
	/**
	 * 图片管理列表页面
	 */
	@RequiresPermissions("test:pic:testPic:list")
	@RequestMapping(value = {"list", ""})
	public String list(TestPic testPic, Model model) {
		model.addAttribute("testPic", testPic);
		return "modules/test/pic/testPicList";
	}
	
		/**
	 * 图片管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("test:pic:testPic:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TestPic testPic, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestPic> page = testPicService.findPage(new Page<TestPic>(request, response), testPic); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑图片管理表单页面
	 */
	@RequiresPermissions(value={"test:pic:testPic:view","test:pic:testPic:add","test:pic:testPic:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TestPic testPic, Model model) {
		model.addAttribute("testPic", testPic);
		return "modules/test/pic/testPicForm";
	}

	/**
	 * 保存图片管理
	 */
	@ResponseBody
	@RequiresPermissions(value={"test:pic:testPic:add","test:pic:testPic:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TestPic testPic, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(testPic);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		testPicService.save(testPic);//保存
		j.setSuccess(true);
		j.setMsg("保存图片管理成功");
		return j;
	}
	
	/**
	 * 删除图片管理
	 */
	@ResponseBody
	@RequiresPermissions("test:pic:testPic:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TestPic testPic) {
		AjaxJson j = new AjaxJson();
		testPicService.delete(testPic);
		j.setMsg("删除图片管理成功");
		return j;
	}
	
	/**
	 * 批量删除图片管理
	 */
	@ResponseBody
	@RequiresPermissions("test:pic:testPic:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			testPicService.delete(testPicService.get(id));
		}
		j.setMsg("删除图片管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("test:pic:testPic:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(TestPic testPic, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "图片管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TestPic> page = testPicService.findPage(new Page<TestPic>(request, response, -1), testPic);
    		new ExportExcel("图片管理", TestPic.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出图片管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("test:pic:testPic:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TestPic> list = ei.getDataList(TestPic.class);
			for (TestPic testPic : list){
				try{
					testPicService.save(testPic);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条图片管理记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条图片管理记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入图片管理失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入图片管理数据模板
	 */
	@ResponseBody
	@RequiresPermissions("test:pic:testPic:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "图片管理数据导入模板.xlsx";
    		List<TestPic> list = Lists.newArrayList(); 
    		new ExportExcel("图片管理数据", TestPic.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}