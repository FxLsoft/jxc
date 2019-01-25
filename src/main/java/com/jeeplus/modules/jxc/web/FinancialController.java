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
import org.springframework.web.bind.annotation.PathVariable;
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
import com.jeeplus.modules.jxc.entity.Financial;
import com.jeeplus.modules.jxc.service.FinancialService;

/**
 * 报表Controller
 * @author 周涛
 * @version 2018-12-22
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/financial")
public class FinancialController extends BaseController {

	@Autowired
	private FinancialService financialService;
	
	@ModelAttribute
	public Financial get(@RequestParam(required=false) String id) {
		Financial entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = financialService.get(id);
		}
		if (entity == null){
			entity = new Financial();
		}
		return entity;
	}
	
	/**
	 * 报表信息列表页面
	 */
	@RequiresPermissions("jxc:financial:list")
	@RequestMapping(value = {"list", ""})
	public String list(Financial financial, Model model) {
		model.addAttribute("financial", financial);
		return "modules/jxc/financialList";
	}
	
		/**
	 * 报表信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:financial:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Financial financial, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Financial> page = financialService.findPage(new Page<Financial>(request, response), financial); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑报表信息表单页面
	 */
	@RequiresPermissions(value={"jxc:financial:view","jxc:financial:add","jxc:financial:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Financial financial, Model model) {
		model.addAttribute("financial", financial);
		model.addAttribute("mode", mode);
		return "modules/jxc/financialForm";
	}

	/**
	 * 保存报表信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:financial:add","jxc:financial:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Financial financial, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(financial);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		financialService.save(financial);//保存
		j.setSuccess(true);
		j.setMsg("保存报表信息成功");
		return j;
	}
	
	/**
	 * 删除报表信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:financial:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Financial financial) {
		AjaxJson j = new AjaxJson();
		financialService.delete(financial);
		j.setMsg("删除报表信息成功");
		return j;
	}
	
	/**
	 * 批量删除报表信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:financial:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			financialService.delete(financialService.get(id));
		}
		j.setMsg("删除报表信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:financial:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Financial financial, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "报表信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Financial> page = financialService.findPage(new Page<Financial>(request, response, -1), financial);
    		new ExportExcel("报表信息", Financial.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("jxc:financial:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Financial> list = ei.getDataList(Financial.class);
			for (Financial financial : list){
				try{
					financialService.save(financial);
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
	@RequiresPermissions("jxc:financial:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "报表信息数据导入模板.xlsx";
    		List<Financial> list = Lists.newArrayList(); 
    		new ExportExcel("报表信息数据", Financial.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}