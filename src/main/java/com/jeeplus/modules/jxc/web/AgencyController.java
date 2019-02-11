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
import com.jeeplus.modules.jxc.entity.Agency;
import com.jeeplus.modules.jxc.service.AgencyService;

/**
 * 经销商Controller
 * @author FxLsoft
 * @version 2019-02-11
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/agency")
public class AgencyController extends BaseController {

	@Autowired
	private AgencyService agencyService;
	
	@ModelAttribute
	public Agency get(@RequestParam(required=false) String id) {
		Agency entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = agencyService.get(id);
		}
		if (entity == null){
			entity = new Agency();
		}
		return entity;
	}
	
	/**
	 * 经销商列表页面
	 */
	@RequiresPermissions("jxc:agency:list")
	@RequestMapping(value = {"list", ""})
	public String list(Agency agency, Model model) {
		model.addAttribute("agency", agency);
		return "modules/jxc/agencyList";
	}
	
		/**
	 * 经销商列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:agency:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Agency agency, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Agency> page = agencyService.findPage(new Page<Agency>(request, response), agency); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑经销商表单页面
	 */
	@RequiresPermissions(value={"jxc:agency:view","jxc:agency:add","jxc:agency:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Agency agency, Model model) {
		model.addAttribute("agency", agency);
		model.addAttribute("mode", mode);
		return "modules/jxc/agencyForm";
	}

	/**
	 * 保存经销商
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:agency:add","jxc:agency:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Agency agency, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(agency);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		agencyService.save(agency);//保存
		j.setSuccess(true);
		j.setMsg("保存经销商成功");
		return j;
	}
	
	/**
	 * 删除经销商
	 */
	@ResponseBody
	@RequiresPermissions("jxc:agency:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Agency agency) {
		AjaxJson j = new AjaxJson();
		agencyService.delete(agency);
		j.setMsg("删除经销商成功");
		return j;
	}
	
	/**
	 * 批量删除经销商
	 */
	@ResponseBody
	@RequiresPermissions("jxc:agency:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			agencyService.delete(agencyService.get(id));
		}
		j.setMsg("删除经销商成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:agency:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Agency agency, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "经销商"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Agency> page = agencyService.findPage(new Page<Agency>(request, response, -1), agency);
    		new ExportExcel("经销商", Agency.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出经销商记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:agency:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Agency> list = ei.getDataList(Agency.class);
			for (Agency agency : list){
				try{
					agencyService.save(agency);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条经销商记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条经销商记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入经销商失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入经销商数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:agency:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "经销商数据导入模板.xlsx";
    		List<Agency> list = Lists.newArrayList(); 
    		new ExportExcel("经销商数据", Agency.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}