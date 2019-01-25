/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.entity.DictType;
import com.jeeplus.modules.sys.entity.DictValue;
import com.jeeplus.modules.sys.service.DictTypeService;

/**
 * 字典Controller
 * @author jeeplus
 * @version 2017-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dict")
public class DictController extends BaseController {

	@Autowired
	private DictTypeService dictTypeService;
	
	@ModelAttribute
	public DictType get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return dictTypeService.get(id);
		}else{
			return new DictType();
		}
	}
	
	@RequiresPermissions("sys:dict:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/dict/dictList";
	}
	
	@ResponseBody
	@RequiresPermissions("sys:dict:list")
	@RequestMapping(value = "getDictValue")
	public Map<String, Object> getDictValue(String dictTypeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(dictTypeId == null || "".equals(dictTypeId)){
			map.put("rows","[]");
			map.put("total",0);
		}else{
			List<DictValue> list = dictTypeService.get(dictTypeId).getDictValueList();
			map.put("rows",list);
			map.put("total", list.size());
		}
		return map;
	}
	
	@ResponseBody
	@RequiresPermissions("sys:dict:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(DictType dictType, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<DictType> page = dictTypeService.findPage(new Page<DictType>(request, response), dictType); 
		return getBootstrapData(page);
	}


	@RequiresPermissions(value={"sys:dict:view","sys:dict:add","sys:dict:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DictType dictType, Model model) {
		model.addAttribute("dictType", dictType);
		return "modules/sys/dict/dictForm";
	}
	
	@RequiresPermissions(value={"sys:dict:view","sys:dict:add","sys:dict:edit"},logical=Logical.OR)
	@RequestMapping(value = "dictValueForm")
	public String dictValueForm(String dictValueId, String dictTypeId, Model model) {
		DictValue dictValue;
		if(dictValueId == null || "".equals(dictValueId)){
			dictValue =  new DictValue();
		}else{
			dictValue = dictTypeService.getDictValue(dictValueId);
		}
		
		dictValue.setDictType(new DictType(dictTypeId));
		model.addAttribute("dictValue", dictValue);
		return "modules/sys/dict/dictValueForm";
	}


	@ResponseBody
	@RequiresPermissions(value={"sys:dict:add","sys:dict:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(DictType dictType, Model model) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(dictType);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		dictTypeService.save(dictType);
		j.setSuccess(true);
		j.setMsg("保存字典类型'" + dictType.getDescription() + "'成功！");
		return j;
	}
	
	@ResponseBody
	@RequiresPermissions(value={"sys:dict:add","sys:dict:edit"},logical=Logical.OR)
	@RequestMapping(value = "saveDictValue")
	public AjaxJson saveDictValue(String dictValueId, DictValue dictValue, Model model) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(dictValue);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		dictValue.setId(dictValueId);
		dictTypeService.saveDictValue(dictValue);
		j.setSuccess(true);
		j.setMsg("保存键值'" + dictValue.getLabel() + "'成功！");
		return j;
	}
	
	
	@ResponseBody
	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "deleteDictValue")
	public AjaxJson deleteDictValue(String dictValueId, Model model) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		dictTypeService.deleteDictValue(new DictValue(dictValueId));
		j.setSuccess(true);
		j.setMsg("删除键值成功！");
		return j;
	}
	
	@ResponseBody
	@RequiresPermissions("sys:dict:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(DictType dictType, Model model) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		dictTypeService.delete(dictType);
		j.setSuccess(true);
		j.setMsg("删除字典成功！");
		return j;
	}
	
	
	/**
	 * 批量删除
	 */
	@ResponseBody
	@RequiresPermissions("sys:dict:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		String idArray[] =ids.split(",");
		for(String id : idArray){
			DictType dictType = dictTypeService.get(id);
			dictTypeService.delete(dictType);
		}
		j.setSuccess(true);
		j.setMsg("删除字典成功！");
		return j;
	}

	
	
	@ResponseBody
	@RequestMapping(value = "listData")
	public List<DictType> listData(@RequestParam(required=false) String type) {
		DictType dictType = new DictType();
		dictType.setType(type);
		return dictTypeService.findList(dictType);
	}

}
