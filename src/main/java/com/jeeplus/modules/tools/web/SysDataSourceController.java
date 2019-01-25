/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tools.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.tools.entity.DbTable;
import com.jeeplus.modules.tools.entity.DbTableColumn;
import com.jeeplus.modules.tools.utils.MultiDBUtils;
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
import com.jeeplus.modules.tools.entity.SysDataSource;
import com.jeeplus.modules.tools.service.SysDataSourceService;

/**
 * 多数据源Controller
 * @author liugf
 * @version 2017-07-27
 */
@Controller
@RequestMapping(value = "${adminPath}/tools/sysDataSource")
public class SysDataSourceController extends BaseController {

	@Autowired
	private SysDataSourceService sysDataSourceService;

	@ModelAttribute
	public SysDataSource get(@RequestParam(required=false) String id) {
		SysDataSource entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysDataSourceService.get(id);
		}
		if (entity == null){
			entity = new SysDataSource();
		}
		return entity;
	}
	
	/**
	 * 数据源列表页面
	 */
	@RequiresPermissions("tools:sysDataSource:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/tools/datasource/sysDataSourceList";
	}
	
		/**
	 * 数据源列表数据
	 */
	@ResponseBody
	@RequiresPermissions("tools:sysDataSource:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SysDataSource sysDataSource, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysDataSource> page = sysDataSourceService.findPage(new Page<SysDataSource>(request, response), sysDataSource); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑数据源表单页面
	 */
	@RequiresPermissions(value={"tools:sysDataSource:view","tools:sysDataSource:add","tools:sysDataSource:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysDataSource sysDataSource, Model model) {
		model.addAttribute("sysDataSource", sysDataSource);
		return "modules/tools/datasource/sysDataSourceForm";
	}

	/**
	 * 保存数据源
	 */
	@ResponseBody
	@RequiresPermissions(value={"tools:sysDataSource:add","tools:sysDataSource:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(SysDataSource sysDataSource, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(sysDataSource);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		sysDataSourceService.save(sysDataSource);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存数据源成功");
		return j;
	}
	
	/**
	 * 删除数据源
	 */
	@ResponseBody
	@RequiresPermissions("tools:sysDataSource:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(SysDataSource sysDataSource) {
		AjaxJson j = new AjaxJson();
		sysDataSourceService.delete(sysDataSource);
		j.setMsg("删除数据源成功");
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "executeSql")
	public AjaxJson executeSql(SysDataSource sysDataSource,String sql) {
		AjaxJson j = new AjaxJson();
		List<Map<String, Object>> result = MultiDBUtils.init(sysDataSource).queryList(sql);
		j.getBody().put("result", result);
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "getTable")
	public AjaxJson getTable(SysDataSource sysDataSource) {
		AjaxJson j = new AjaxJson();
		DbTable dbTable = new DbTable();
		dbTable.setName(sysDataSource.getName());
		List<DbTable> tableList = sysDataSourceService.findTableList(sysDataSource);
		j.getBody().put("result", tableList);
		return j;
	}
	@ResponseBody
	@RequestMapping(value = "getColumn")
	public AjaxJson getColumn(SysDataSource sysDataSource, String tableName) {
		AjaxJson j = new AjaxJson();
		DbTable dbTable = new DbTable();
		dbTable.setName(sysDataSource.getName());
		List<DbTableColumn> columnsList = sysDataSourceService.findTableColumnList(sysDataSource, tableName);
		j.getBody().put("result", columnsList);
		return j;
	}


	/**
	 * 查看，增加，编辑数据源表单页面
	 */
	@RequestMapping(value = "test")
	public String test(SysDataSource sysDataSource, Model model) {
		List list = sysDataSourceService.findList(new SysDataSource());
		model.addAttribute("sysDataSource", sysDataSource);
		model.addAttribute("sysDataSourceList", list);
		return "modules/tools/datasource/sysDataSourceTest";
	}
	
	/**
	 * 批量删除数据源
	 */
	@ResponseBody
	@RequiresPermissions("tools:sysDataSource:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysDataSourceService.delete(sysDataSourceService.get(id));
		}
		j.setMsg("删除数据源成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("tools:sysDataSource:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(SysDataSource sysDataSource, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "数据源"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysDataSource> page = sysDataSourceService.findPage(new Page<SysDataSource>(request, response, -1), sysDataSource);
    		new ExportExcel("数据源", SysDataSource.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出数据源记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("tools:sysDataSource:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SysDataSource> list = ei.getDataList(SysDataSource.class);
			for (SysDataSource sysDataSource : list){
				try{
					sysDataSourceService.save(sysDataSource);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条数据源记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条数据源记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入数据源失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/tools/datasource/sysDataSource/?repage";
    }
	
	/**
	 * 下载导入数据源数据模板
	 */
	@RequiresPermissions("tools:sysDataSource:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "数据源数据导入模板.xlsx";
    		List<SysDataSource> list = Lists.newArrayList(); 
    		new ExportExcel("数据源数据", SysDataSource.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/tools/datasource/sysDataSource/?repage";
    }

}