/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.note.web;

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
import com.jeeplus.modules.test.note.entity.TestNote;
import com.jeeplus.modules.test.note.service.TestNoteService;

/**
 * 富文本测试Controller
 * @author liugf
 * @version 2018-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/test/note/testNote")
public class TestNoteController extends BaseController {

	@Autowired
	private TestNoteService testNoteService;
	
	@ModelAttribute
	public TestNote get(@RequestParam(required=false) String id) {
		TestNote entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testNoteService.get(id);
		}
		if (entity == null){
			entity = new TestNote();
		}
		return entity;
	}
	
	/**
	 * 富文本测试列表页面
	 */
	@RequiresPermissions("test:note:testNote:list")
	@RequestMapping(value = {"list", ""})
	public String list(TestNote testNote, Model model) {
		model.addAttribute("testNote", testNote);
		return "modules/test/note/testNoteList";
	}
	
		/**
	 * 富文本测试列表数据
	 */
	@ResponseBody
	@RequiresPermissions("test:note:testNote:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TestNote testNote, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestNote> page = testNoteService.findPage(new Page<TestNote>(request, response), testNote); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑富文本测试表单页面
	 */
	@RequiresPermissions(value={"test:note:testNote:view","test:note:testNote:add","test:note:testNote:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, TestNote testNote, Model model) {
		model.addAttribute("testNote", testNote);
		model.addAttribute("mode", mode);
		return "modules/test/note/testNoteForm";
	}

	/**
	 * 保存富文本测试
	 */
	@ResponseBody
	@RequiresPermissions(value={"test:note:testNote:add","test:note:testNote:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TestNote testNote, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(testNote);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		testNoteService.save(testNote);//保存
		j.setSuccess(true);
		j.setMsg("保存富文本测试成功");
		return j;
	}
	
	/**
	 * 删除富文本测试
	 */
	@ResponseBody
	@RequiresPermissions("test:note:testNote:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TestNote testNote) {
		AjaxJson j = new AjaxJson();
		testNoteService.delete(testNote);
		j.setMsg("删除富文本测试成功");
		return j;
	}
	
	/**
	 * 批量删除富文本测试
	 */
	@ResponseBody
	@RequiresPermissions("test:note:testNote:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			testNoteService.delete(testNoteService.get(id));
		}
		j.setMsg("删除富文本测试成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("test:note:testNote:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(TestNote testNote, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "富文本测试"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TestNote> page = testNoteService.findPage(new Page<TestNote>(request, response, -1), testNote);
    		new ExportExcel("富文本测试", TestNote.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出富文本测试记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("test:note:testNote:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TestNote> list = ei.getDataList(TestNote.class);
			for (TestNote testNote : list){
				try{
					testNoteService.save(testNote);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条富文本测试记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条富文本测试记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入富文本测试失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入富文本测试数据模板
	 */
	@ResponseBody
	@RequiresPermissions("test:note:testNote:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "富文本测试数据导入模板.xlsx";
    		List<TestNote> list = Lists.newArrayList(); 
    		new ExportExcel("富文本测试数据", TestNote.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}