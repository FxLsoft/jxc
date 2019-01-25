/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.entity.DataRule;
import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.mapper.MenuMapper;
import com.jeeplus.modules.sys.service.DataRuleService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 数据权限Controller
 * @author lgf
 * @version 2017-04-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dataRule")
public class DataRuleController extends BaseController {

	@Autowired
	private DataRuleService dataRuleService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private MenuMapper menuMapper;
	
	@ModelAttribute
	public DataRule get(@RequestParam(required=false) String id) {
		DataRule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dataRuleService.get(id);
		}
		if (entity == null){
			entity = new DataRule();
		}
		return entity;
	}
	
	/**
	 * 数据权限列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/dataRuleList";
	}
	
		/**
	 * 数据权限列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(DataRule dataRule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DataRule> page = dataRuleService.findPage(new Page<DataRule>(request, response), dataRule); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑数据权限表单页面
	 */
	@RequestMapping(value = "form")
	public String form(DataRule dataRule, Model model) {
		model.addAttribute("dataRule", dataRule);
		if(StringUtils.isBlank(dataRule.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/menu/dataRuleForm";
	}

	/**
	 * 保存数据权限
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(DataRule dataRule, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(dataRule);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
        dataRuleService.save(dataRule);//保存
		//清除缓存
		UserUtils.removeCache(UserUtils.CACHE_DATA_RULE_LIST);
		
		j.setSuccess(true);
		j.setMsg("保存数据权限成功!");
		return j;
	}
	
	/**
	 * 删除数据权限
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(DataRule dataRule) {
		AjaxJson j = new AjaxJson();
		dataRuleService.delete(dataRule);
		//清除缓存
		UserUtils.removeCache(UserUtils.CACHE_DATA_RULE_LIST);
		
		j.setMsg("删除数据权限成功");
		return j;
	}
	
	/**
	 * 批量删除数据权限
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dataRuleService.delete(dataRuleService.get(id));
		}
		//清除缓存
		UserUtils.removeCache(UserUtils.CACHE_DATA_RULE_LIST);
		
		j.setMsg("删除数据权限成功");
		return j;
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
		String dataRuleIds = "";
		if(role !=null){
			dataRuleIds = ","+role.getDataRuleIds()+",";
		}
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list =  menuMapper.findAllDataRuleList(new Menu());
		HashSet<String> existIdSet = new HashSet<String>();
		for (int i=0; i<list.size(); i++){
			Menu menu = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(menu.getId()) && menu.getParentIds().indexOf(","+extId+",")==-1)){
				if(isShowHide != null && isShowHide.equals("0") && menu.getIsShow().equals("0")){
					continue;
				}
				
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", menu.getId());
				map.put("text", menu.getName());
				map.put("name", menu.getName());
				if(StringUtils.isNotBlank(menu.getIcon())){
					map.put("icon", menu.getIcon());
				}
				if("0".equals(menu.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);//默认一级展开
					map.put("state", state);
					
				}else{
					map.put("parent", menu.getParentId());
				}
			
				
				boolean existDataRule = false;
				List<DataRule> dataRuleList = menu.getDataRuleList();
				for(DataRule dataRule : dataRuleList){
					Map<String, Object> dataRuleMap = Maps.newHashMap();
					dataRuleMap.put("id", dataRule.getId());
					dataRuleMap.put("text", dataRule.getName());
					dataRuleMap.put("name", dataRule.getName());
					dataRuleMap.put("parent", dataRule.getMenuId());
					dataRuleMap.put("type", "4");
					if(dataRuleIds.contains(","+dataRule.getId()+",")){
						Map<String, Object> state = Maps.newHashMap();
						state.put("selected", true);
						dataRuleMap.put("state", state);
					}
					mapList.add(dataRuleMap);
					existDataRule = true;
				}
				if(existDataRule){
					if(!existIdSet.contains(menu.getId())){
						mapList.add(map);
						existIdSet.add(menu.getId());
					}
					
					String[] parentIds = menu.getParentIds().split(",");
					for(String parentId : parentIds){
						if(!existIdSet.contains(parentId)){
							existIdSet.add(parentId);
							Menu parentMenu = systemService.getMenu(parentId);
							if(parentMenu == null){
								continue;
							}
							Map<String, Object> parentMenuMap = Maps.newHashMap();
							parentMenuMap.put("id", parentId);
							parentMenuMap.put("text", parentMenu.getName());
							parentMenuMap.put("name", parentMenu.getName());
							if("0".equals(parentMenu.getParentId())){
								parentMenuMap.put("parent", "#");
								Map<String, Object> state = Maps.newHashMap();
								state.put("opened", true);//默认一级展开
								parentMenuMap.put("state", state);
								
							}else{
								parentMenuMap.put("parent", parentMenu.getParentId());
							}
							mapList.add(parentMenuMap);
							
						}
					}
					
				}
				
			}
		}
		return mapList;
	}
	
	
	

}