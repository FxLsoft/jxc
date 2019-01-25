/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import java.util.ArrayList;
import java.util.HashSet;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.utils.DictUtils;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 机构Controller
 * @author jeeplus
 * @version 2016-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return officeService.get(id);
		}else{
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:list")
	@RequestMapping(value = {"", "list"})
	public String list(Office office, Model model) {
		if(office==null || office.getParentIds() == null){
			 model.addAttribute("list", officeService.findList(false));
		}else{
			 model.addAttribute("list", officeService.findList(office));
		}
		return "modules/sys/office/officeList";
	}
	
	@RequiresPermissions(value={"sys:office:view","sys:office:add","sys:office:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}
		model.addAttribute("office", office);
		return "modules/sys/office/officeForm";
	}
	
	@ResponseBody
	@RequiresPermissions(value={"sys:office:add","sys:office:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Office office, Model model) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(office);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		officeService.save(office);
		
		if(office.getChildDeptList()!=null){
			Office childOffice = null;
			for(String id : office.getChildDeptList()){
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+1));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}
		
		j.setSuccess(true);
		j.setMsg("保存机构'" + office.getName() + "'成功");
		j.put("office", office);
		return j;
	}
	
	@ResponseBody
	@RequiresPermissions("sys:office:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Office office) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		officeService.delete(office);
		j.setSuccess(true);
		j.setMsg("删除成功！");
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<Office> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return officeService.getChildren(parentId);
	}
	
	
	
	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		HashSet existIds = new HashSet();
		for(Office o : list){
			existIds.add(o.getId());
		}
		for (int i=0; i<list.size(); i++){
			Office office = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(office.getId()) && office.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(office.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(office.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(office.getUseable())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", office.getId());
				if("0".equals(office.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);
				}else{
					if(existIds.contains(office.getParentId())){
						map.put("parent", office.getParentId());
					}else{
						map.put("parent", "#");
					}
				}
				map.put("name", office.getName());
				map.put("text", office.getName());
				map.put("type", office.getType());
				if ("1".equals(office.getType()) && "2".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "bootstrapTreeData")
	public List<Map<String, Object>> bootstrapTreeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList(); 
		List<Office> roots = officeService.getChildren("0");
		for(Office root:roots){
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", root.getId());
			map.put("name", root.getName());
			map.put("level", 1);
			deepTree(map, root);
			mapList.add(map);
		}
		return mapList;
	}
	
	public void deepTree(Map<String, Object> map, Office office){
	
		map.put("text", office.getName());
		List<Map<String, Object>> arra = new ArrayList<Map<String, Object>>();
		for(Office child:officeService.getChildren(office.getId())){
			Map<String, Object> childMap = Maps.newHashMap();
			childMap.put("id", child.getId());
			childMap.put("name", child.getName());
			arra.add(childMap);
			deepTree(childMap, child);
		}
		if(arra.size() >0){
			map.put("children", arra);
		}
	}
}
