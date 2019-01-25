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
import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 菜单Controller
 * @author jeeplus
 * @version 2016-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/menu")
public class MenuController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@ModelAttribute("menu")
	public Menu get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getMenu(id);
		}else{
			return new Menu();
		}
	}

	@RequiresPermissions("sys:menu:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		return "modules/sys/menu/menuList";
	}

	@RequiresPermissions(value={"sys:menu:view","sys:menu:add","sys:menu:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Menu menu, Model model) {
		if (menu.getParent()==null||menu.getParent().getId()==null){
			menu.setParent(new Menu(Menu.getRootId()));
		}
		menu.setParent(systemService.getMenu(menu.getParent().getId()));
		model.addAttribute("menu", menu);
		return "modules/sys/menu/menuForm";
	}
	
	@ResponseBody
	@RequiresPermissions(value={"sys:menu:add","sys:menu:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Menu menu, Model model) {
		AjaxJson j = new AjaxJson();
		if(!UserUtils.getUser().isAdmin()){
			j.setSuccess(false);
			j.setMsg("越权操作，只有超级管理员才能添加或修改数据！");
			return j;
		}
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(menu);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		
		// 获取排序号，最末节点排序号+30
		if (StringUtils.isBlank(menu.getId())){
			List<Menu> list = Lists.newArrayList();
			List<Menu> sourcelist = systemService.findAllMenu();
			Menu.sortList(list, sourcelist, menu.getParentId(), false);
			if (list.size() > 0){
				menu.setSort(list.get(list.size()-1).getSort() + 30);
			}
		}
		
		systemService.saveMenu(menu);
		j.setMsg("保存成功!");
		j.put("menu", menu);
		return j;
	}
	
	@RequiresPermissions("sys:menu:del")
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(Menu menu) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作");
			return j;
		}
		systemService.deleteMenu(menu);
		j.setMsg("删除成功!");
		return  j;
	}

	@RequiresPermissions("sys:menu:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作");
			return j;
		}
		String idArray[] =ids.split(",");
		for(String id : idArray){
			Menu menu = systemService.getMenu(id);
			if(menu != null){
				systemService.deleteMenu(systemService.getMenu(id));
			}
		}
			
		j.setMsg("删除菜单成功！");
		return j;
	}

	/**
	 * 修改菜单排序
	 */
	@RequiresPermissions("sys:menu:updateSort")
	@ResponseBody
	@RequestMapping(value = "sort")
	public AjaxJson sort(String id1, int sort1, String id2, int sort2) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		Menu menu = new Menu();
		menu.setId(id1);
		menu.setSort(sort1);
		systemService.updateMenuSort(menu);
		menu.setId(id2);
		menu.setSort(sort2);
		systemService.updateMenuSort(menu);
		j.setMsg("排序成功！");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<Menu> getChildren(String parentId){
		if("-1".equals(parentId)){
			parentId = "1";
		}
		return systemService.getChildren(parentId);
	}
	
	/**
	 * isShowHide是否显示隐藏菜单
	 * @param extId
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String roleId, @RequestParam(required=false) String extId,@RequestParam(required=false) String isShowHide, HttpServletResponse response) {
		Role role = systemService.getRole(roleId);
		String menuIds = "";
		if(role !=null){
			menuIds = ","+role.getMenuIds()+",";
		}
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list = systemService.findAllMenu();
		for (int i=0; i<list.size(); i++){
			Menu e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				if(isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")){
					continue;
				}
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				if("0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);
					
				}else{
					if(i == 0){
						map.put("parent", "#");
					}else{
						map.put("parent", e.getParentId());
					}
				}

				if(menuIds.contains(","+e.getId()+",")&& systemService.getChildren(e.getId()).size() == 0){
					Map<String, Object> state = Maps.newHashMap();
					state.put("selected", true);
					map.put("state", state);
				}
				
				if(StringUtils.isNotBlank(e.getIcon())){
					map.put("icon", e.getIcon());
				}
				if("2".equals(e.getType())){
					map.put("type", "btn");
				}else if("1".equals(e.getType())){
					map.put("type", "menu");
				}
				map.put("text", e.getName());
				map.put("name", e.getName());
				
				mapList.add(map);
			}
		}
		return mapList;
	}
}
