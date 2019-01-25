/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.grid.web;

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
import com.jeeplus.modules.test.grid.entity.TestContinent;
import com.jeeplus.modules.test.grid.service.TestContinentService;

/**
 * 洲Controller
 * @author lgf
 * @version 2018-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/test/grid/testContinent")
public class TestContinentController extends BaseController {

	@Autowired
	private TestContinentService testContinentService;
	
	@ModelAttribute
	public TestContinent get(@RequestParam(required=false) String id) {
		TestContinent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testContinentService.get(id);
		}
		if (entity == null){
			entity = new TestContinent();
		}
		return entity;
	}
	
	/**
	 * 洲列表页面
	 */
	@RequiresPermissions("test:grid:testContinent:list")
	@RequestMapping(value = {"list", ""})
	public String list(TestContinent testContinent, Model model) {
		model.addAttribute("testContinent", testContinent);
		return "modules/test/grid/testContinentList";
	}
	
		/**
	 * 洲列表数据
	 */
	@ResponseBody
	@RequiresPermissions("test:grid:testContinent:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TestContinent testContinent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestContinent> page = testContinentService.findPage(new Page<TestContinent>(request, response), testContinent); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑洲表单页面
	 */
	@RequiresPermissions(value={"test:grid:testContinent:view","test:grid:testContinent:add","test:grid:testContinent:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TestContinent testContinent, Model model) {
		model.addAttribute("testContinent", testContinent);
		return "modules/test/grid/testContinentForm";
	}

	/**
	 * 保存洲
	 */
	@ResponseBody
	@RequiresPermissions(value={"test:grid:testContinent:add","test:grid:testContinent:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TestContinent testContinent, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(testContinent);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		testContinentService.save(testContinent);//保存
		j.setSuccess(true);
		j.setMsg("保存洲成功");
		return j;
	}
	
	/**
	 * 删除洲
	 */
	@ResponseBody
	@RequiresPermissions("test:grid:testContinent:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TestContinent testContinent) {
		AjaxJson j = new AjaxJson();
		testContinentService.delete(testContinent);
		j.setMsg("删除洲成功");
		return j;
	}
	
	/**
	 * 批量删除洲
	 */
	@ResponseBody
	@RequiresPermissions("test:grid:testContinent:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			testContinentService.delete(testContinentService.get(id));
		}
		j.setMsg("删除洲成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("test:grid:testContinent:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(TestContinent testContinent, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "洲"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TestContinent> page = testContinentService.findPage(new Page<TestContinent>(request, response, -1), testContinent);
    		new ExportExcel("洲", TestContinent.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出洲记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("test:grid:testContinent:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TestContinent> list = ei.getDataList(TestContinent.class);
			for (TestContinent testContinent : list){
				try{
					testContinentService.save(testContinent);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条洲记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条洲记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入洲失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入洲数据模板
	 */
	@ResponseBody
	@RequiresPermissions("test:grid:testContinent:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "洲数据导入模板.xlsx";
    		List<TestContinent> list = Lists.newArrayList(); 
    		new ExportExcel("洲数据", TestContinent.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}