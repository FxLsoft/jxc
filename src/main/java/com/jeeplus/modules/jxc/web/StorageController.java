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
import com.jeeplus.modules.jxc.entity.Storage;
import com.jeeplus.modules.jxc.service.StorageService;

/**
 * 库存Controller
 * @author FxLsoft
 * @version 2019-02-11
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/storage")
public class StorageController extends BaseController {

	@Autowired
	private StorageService storageService;
	
	@ModelAttribute
	public Storage get(@RequestParam(required=false) String id) {
		Storage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = storageService.get(id);
		}
		if (entity == null){
			entity = new Storage();
		}
		return entity;
	}
	
	/**
	 * 库存列表页面
	 */
	@RequiresPermissions("jxc:storage:list")
	@RequestMapping(value = {"list", ""})
	public String list(Storage storage, Model model) {
		model.addAttribute("storage", storage);
		return "modules/jxc/storageList";
	}
	
		/**
	 * 库存列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:storage:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Storage storage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Storage> page = storageService.findPage(new Page<Storage>(request, response), storage); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑库存表单页面
	 */
	@RequiresPermissions(value={"jxc:storage:view","jxc:storage:add","jxc:storage:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Storage storage, Model model) {
		model.addAttribute("storage", storage);
		model.addAttribute("mode", mode);
		return "modules/jxc/storageForm";
	}

	/**
	 * 保存库存
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:storage:add","jxc:storage:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Storage storage, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(storage);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		storageService.save(storage);//保存
		j.setSuccess(true);
		j.setMsg("保存库存成功");
		return j;
	}
	
	/**
	 * 删除库存
	 */
	@ResponseBody
	@RequiresPermissions("jxc:storage:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Storage storage) {
		AjaxJson j = new AjaxJson();
		storageService.delete(storage);
		j.setMsg("删除库存成功");
		return j;
	}
	
	/**
	 * 批量删除库存
	 */
	@ResponseBody
	@RequiresPermissions("jxc:storage:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			storageService.delete(storageService.get(id));
		}
		j.setMsg("删除库存成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:storage:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Storage storage, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "库存"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Storage> page = storageService.findPage(new Page<Storage>(request, response, -1), storage);
    		new ExportExcel("库存", Storage.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出库存记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:storage:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Storage> list = ei.getDataList(Storage.class);
			for (Storage storage : list){
				try{
					storageService.save(storage);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条库存记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条库存记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入库存失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入库存数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:storage:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "库存数据导入模板.xlsx";
    		List<Storage> list = Lists.newArrayList(); 
    		new ExportExcel("库存数据", Storage.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}