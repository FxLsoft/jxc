/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.service.AreaService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 区域Controller
 * @author jeeplus
 * @version 2016-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/area")
public class AreaController extends BaseController {

	@Autowired
	private AreaService areaService;
	
	@ModelAttribute("area")
	public Area get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return areaService.get(id);
		}else{
			return new Area();
		}
	}

	@RequiresPermissions("sys:area:list")
	@RequestMapping(value = {"list", ""})
	public String list(Area area, Model model) {
		return "modules/sys/area/areaList";
	}

	@RequiresPermissions(value={"sys:area:view","sys:area:add","sys:area:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Area area, Model model) {
		if (area.getParent()==null||area.getParent().getId()==null){
			area.setParent(UserUtils.getUser().getOffice().getArea());
		}else{
			area.setParent(areaService.get(area.getParent().getId()));
		}
		model.addAttribute("area", area);
		return "modules/sys/area/areaForm";
	}
	
	@RequiresPermissions(value={"sys:area:add","sys:area:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxJson save(Area area, Model model) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}

		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(area);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		areaService.save(area);
		j.setSuccess(true);
		j.put("area", area);
		j.setMsg("保存成功！");
		return j;
	}
	
	@RequiresPermissions("sys:area:del")
	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxJson delete(Area area) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		areaService.delete(area);
		j.setSuccess(true);
		j.setMsg("删除区域成功！");
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<Area> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return areaService.getChildren(parentId);
	}
	
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Area> list = areaService.findAll();
		for (int i=0; i<list.size(); i++){
			Area e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				if("0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}
				map.put("name", e.getName());
				map.put("text", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}
