/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.jeeplus.modules.jxc.entity.Report;
import com.jeeplus.modules.jxc.service.ReportService;

/**
 * 财务报表Controller
 * @author FxLsoft
 * @version 2019-02-23
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/report")
public class ReportController extends BaseController {

	@Autowired
	private ReportService reportService;
	
	@ModelAttribute
	public Report get(@RequestParam(required=false) String id) {
		Report entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reportService.get(id);
		}
		if (entity == null){
			entity = new Report();
		}
		return entity;
	}
	
	/**
	 * 报表信息列表页面
	 */
	@RequiresPermissions("jxc:report:list")
	@RequestMapping(value = {"list", ""})
	public String list(Report report, Model model) {
		model.addAttribute("report", report);
		return "modules/jxc/reportList";
	}
	
		/**
	 * 报表信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:report:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Report report, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Report> page = reportService.findPage(new Page<Report>(request, response), report); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑报表信息表单页面
	 */
	@RequiresPermissions(value={"jxc:report:view","jxc:report:add","jxc:report:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Report report, Model model) {
		model.addAttribute("report", report);
		model.addAttribute("mode", mode);
		return "modules/jxc/reportForm";
	}

	/**
	 * 保存报表信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:report:add","jxc:report:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Report report, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(report);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		reportService.save(report);//保存
		j.setSuccess(true);
		j.setMsg("保存报表信息成功");
		return j;
	}
	
	/**
	 * 删除报表信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:report:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Report report) {
		AjaxJson j = new AjaxJson();
		reportService.delete(report);
		j.setMsg("删除报表信息成功");
		return j;
	}
	
	/**
	 * 批量删除报表信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:report:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			reportService.delete(reportService.get(id));
		}
		j.setMsg("删除报表信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:report:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Report report, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "报表信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Report> page = reportService.findPage(new Page<Report>(request, response, -1), report);
    		new ExportExcel("报表信息", Report.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出报表信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:report:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Report> list = ei.getDataList(Report.class);
			for (Report report : list){
				try{
					reportService.save(report);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条报表信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条报表信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入报表信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入报表信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:report:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "报表信息数据导入模板.xlsx";
    		List<Report> list = Lists.newArrayList(); 
    		new ExportExcel("报表信息数据", Report.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}