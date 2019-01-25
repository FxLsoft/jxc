/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.onetomany.dialog.web;

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
import com.jeeplus.modules.test.onetomany.dialog.entity.TestDataMain1;
import com.jeeplus.modules.test.onetomany.dialog.service.TestDataMain1Service;

/**
 * 票务代理Controller
 * @author liugf
 * @version 2018-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/test/onetomany/dialog/testDataMain1")
public class TestDataMain1Controller extends BaseController {

	@Autowired
	private TestDataMain1Service testDataMain1Service;
	
	@ModelAttribute
	public TestDataMain1 get(@RequestParam(required=false) String id) {
		TestDataMain1 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testDataMain1Service.get(id);
		}
		if (entity == null){
			entity = new TestDataMain1();
		}
		return entity;
	}
	
	/**
	 * 票务代理列表页面
	 */
	@RequiresPermissions("test:onetomany:dialog:testDataMain1:list")
	@RequestMapping(value = {"list", ""})
	public String list(TestDataMain1 testDataMain1, Model model) {
		model.addAttribute("testDataMain1", testDataMain1);
		return "modules/test/onetomany/dialog/testDataMain1List";
	}
	
		/**
	 * 票务代理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:dialog:testDataMain1:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TestDataMain1 testDataMain1, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestDataMain1> page = testDataMain1Service.findPage(new Page<TestDataMain1>(request, response), testDataMain1); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑票务代理表单页面
	 */
	@RequiresPermissions(value={"test:onetomany:dialog:testDataMain1:view","test:onetomany:dialog:testDataMain1:add","test:onetomany:dialog:testDataMain1:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TestDataMain1 testDataMain1, Model model) {
		model.addAttribute("testDataMain1", testDataMain1);
		return "modules/test/onetomany/dialog/testDataMain1Form";
	}

	/**
	 * 保存票务代理
	 */
	@ResponseBody
	@RequiresPermissions(value={"test:onetomany:dialog:testDataMain1:add","test:onetomany:dialog:testDataMain1:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TestDataMain1 testDataMain1, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(testDataMain1);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		testDataMain1Service.save(testDataMain1);//保存
		j.setSuccess(true);
		j.setMsg("保存票务代理成功");
		return j;
	}
	
	/**
	 * 删除票务代理
	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:dialog:testDataMain1:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TestDataMain1 testDataMain1) {
		AjaxJson j = new AjaxJson();
		testDataMain1Service.delete(testDataMain1);
		j.setMsg("删除票务代理成功");
		return j;
	}
	
	/**
	 * 批量删除票务代理
	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:dialog:testDataMain1:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			testDataMain1Service.delete(testDataMain1Service.get(id));
		}
		j.setMsg("删除票务代理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:dialog:testDataMain1:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(TestDataMain1 testDataMain1, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "票务代理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TestDataMain1> page = testDataMain1Service.findPage(new Page<TestDataMain1>(request, response, -1), testDataMain1);
    		new ExportExcel("票务代理", TestDataMain1.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出票务代理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public TestDataMain1 detail(String id) {
		return testDataMain1Service.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:dialog:testDataMain1:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TestDataMain1> list = ei.getDataList(TestDataMain1.class);
			for (TestDataMain1 testDataMain1 : list){
				try{
					testDataMain1Service.save(testDataMain1);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条票务代理记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条票务代理记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入票务代理失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入票务代理数据模板
	 */
	@ResponseBody
	@RequiresPermissions("test:onetomany:dialog:testDataMain1:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "票务代理数据导入模板.xlsx";
    		List<TestDataMain1> list = Lists.newArrayList(); 
    		new ExportExcel("票务代理数据", TestDataMain1.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}