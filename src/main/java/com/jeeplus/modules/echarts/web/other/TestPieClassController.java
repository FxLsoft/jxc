/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.echarts.web.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.data.LineData;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Funnel;
import com.github.abel533.echarts.series.Pie;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.echarts.entity.other.TestPieClass;
import com.jeeplus.modules.echarts.service.other.TestPieClassService;

/**
 * 学生Controller
 * @author lgf
 * @version 2017-06-04
 */
@Controller
@RequestMapping(value = "${adminPath}/echarts/other/testPieClass")
public class TestPieClassController extends BaseController {

	@Autowired
	private TestPieClassService testPieClassService;
	
	@ModelAttribute
	public TestPieClass get(@RequestParam(required=false) String id) {
		TestPieClass entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testPieClassService.get(id);
		}
		if (entity == null){
			entity = new TestPieClass();
		}
		return entity;
	}

	@ResponseBody
	@RequestMapping("option")
	public GsonOption getOption(){
		GsonOption option = new GsonOption();
		//timeline变态的地方在于多个Option
		option.title().text("班级人数比").subtext("测试数据");
		option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage, new MagicType(Magic.pie, Magic.funnel)
				.option(new MagicType.Option().funnel(
						new Funnel().x("25%").width("50%").funnelAlign(X.left).max(1548))));

		//构造11个数据
		List<TestPieClass> list = testPieClassService.findList(new TestPieClass());
		ArrayList arrayList = new ArrayList();
		for(TestPieClass p:list){
			arrayList.add(new PieData(p.getClassName(), p.getNum()));
		}
		Pie pie = new Pie().name("班级人数比");
		pie.setData(arrayList);
		option.series(pie);;
		return option;
	}
	/**
	 * 学生列表页面
	 */
	@RequiresPermissions("echarts:other:testPieClass:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/echarts/other/testPieClassList";
	}
	
		/**
	 * 学生列表数据
	 */
	@ResponseBody
	@RequiresPermissions("echarts:other:testPieClass:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TestPieClass testPieClass, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestPieClass> page = testPieClassService.findPage(new Page<TestPieClass>(request, response), testPieClass); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑学生表单页面
	 */
	@RequiresPermissions(value={"echarts:other:testPieClass:view","echarts:other:testPieClass:add","echarts:other:testPieClass:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TestPieClass testPieClass, Model model) {
		model.addAttribute("testPieClass", testPieClass);
		return "modules/echarts/other/testPieClassForm";
	}

	/**
	 * 保存学生
	 */
	@ResponseBody
	@RequiresPermissions(value={"echarts:other:testPieClass:add","echarts:other:testPieClass:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TestPieClass testPieClass, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(testPieClass);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		testPieClassService.save(testPieClass);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存学生成功");
		return j;
	}
	
	/**
	 * 删除学生
	 */
	@ResponseBody
	@RequiresPermissions("echarts:other:testPieClass:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TestPieClass testPieClass) {
		AjaxJson j = new AjaxJson();
		testPieClassService.delete(testPieClass);
		j.setMsg("删除学生成功");
		return j;
	}
	
	/**
	 * 批量删除学生
	 */
	@ResponseBody
	@RequiresPermissions("echarts:other:testPieClass:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			testPieClassService.delete(testPieClassService.get(id));
		}
		j.setMsg("删除学生成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("echarts:other:testPieClass:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(TestPieClass testPieClass, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "学生"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TestPieClass> page = testPieClassService.findPage(new Page<TestPieClass>(request, response, -1), testPieClass);
    		new ExportExcel("学生", TestPieClass.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出学生记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("echarts:other:testPieClass:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TestPieClass> list = ei.getDataList(TestPieClass.class);
			for (TestPieClass testPieClass : list){
				try{
					testPieClassService.save(testPieClass);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条学生记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条学生记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入学生失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/echarts/other/testPieClass/?repage";
    }
	
	/**
	 * 下载导入学生数据模板
	 */
	@RequiresPermissions("echarts:other:testPieClass:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "学生数据导入模板.xlsx";
    		List<TestPieClass> list = Lists.newArrayList(); 
    		new ExportExcel("学生数据", TestPieClass.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/echarts/other/testPieClass/?repage";
    }

}