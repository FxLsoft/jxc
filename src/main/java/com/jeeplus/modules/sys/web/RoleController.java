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
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 角色Controller
 * @author jeeplus
 * @version 2016-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/role")
public class RoleController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@ModelAttribute("role")
	public Role get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getRole(id);
		}else{
			return new Role();
		}
	}
	
	@RequiresPermissions("sys:role:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/role/roleList";
	}
	
	@ResponseBody
	@RequiresPermissions("sys:role:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Role role) {
		List<Role> list = systemService.findAllRole();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", list.size());
		return map;
	}

	@RequiresPermissions(value={"sys:role:view","sys:role:add","sys:role:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Role role, Model model) {
		if (role.getOffice()==null){
			role.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("role", role);
		return "modules/sys/role/roleForm";
	}
	
	@RequiresPermissions("sys:role:auth")
	@RequestMapping(value = "auth")
	public String auth(Role role, Model model) {
		if (role.getOffice()==null){
			role.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("role", role);
		return "modules/sys/role/roleAuth";
	}
	
	@RequiresPermissions("sys:role:auth")
	@RequestMapping(value = "dataRule")
	public String dataRule(Role role, Model model) {
		if (role.getOffice()==null){
			role.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("role", role);
		return "modules/sys/role/roleDataRule";
	}
	
	
	@ResponseBody
	@RequiresPermissions(value={"sys:role:assign","sys:role:auth","sys:role:add","sys:role:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Role role, Model model) {
		AjaxJson j = new AjaxJson();
		if(!UserUtils.getUser().isAdmin()&&role.getSysData().equals(Global.YES)){
			j.setSuccess(false);
			j.setMsg("越权操作，只有超级管理员才能修改此数据！");
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
		String errMsg = beanValidator(role);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		if (!"true".equals(checkName(role.getOldName(), role.getName()))){
			j.setSuccess(false);
			j.setMsg( "保存角色'" + role.getName() + "'失败, 角色名已存在");
			return j;
		}
		if (!"true".equals(checkEnname(role.getOldEnname(), role.getEnname()))){
			j.setSuccess(false);
			j.setMsg("保存角色'" + role.getName() + "'失败, 英文名已存在");
			return j;
		}
		systemService.saveRole(role);
		j.setSuccess(true);
		j.setMsg("保存角色'" + role.getName() + "'成功");
		return j;
	}
	
	/**
	 * 删除角色
	 */
	@ResponseBody
	@RequiresPermissions("sys:role:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		String idArray[] =ids.split(",");
		StringBuffer msg = new StringBuffer();
		for(String id : idArray){
			Role role = systemService.getRole(id);
			if(!UserUtils.getUser().isAdmin() && role.getSysData().equals(Global.YES)){
				msg.append( "越权操作，只有超级管理员才能修改["+role.getName()+"]数据！<br/>");
			}else{
				systemService.deleteRole(role);
				msg.append( "删除角色["+role.getName()+"]成功<br/>");
				
			}
		}
		j.setSuccess(true);
		j.setMsg(msg.toString());
		return j;
	}
	
	/**
	 * 获取所属角色用户
	 * @param role
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:role:assign")
	@RequestMapping(value = "assign")
	public Map<String, Object> assign(HttpServletRequest request, HttpServletResponse response,Role role, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), new User(new Role(role.getId())));
		return getBootstrapData(page);
	}
	
	
	
	/**
	 * 角色分配 -- 从角色中移除用户
	 * @param userId
	 * @param roleId
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:role:assign")
	@RequestMapping(value = "outrole")
	public AjaxJson outrole(String userId, String roleId) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setMsg("演示模式，不允许操作！");
			j.setSuccess(false);
			return j;
		}
		Role role = systemService.getRole(roleId);
		User user = systemService.getUser(userId);
		if (UserUtils.getUser().getId().equals(userId)) {
			j.setMsg("无法从角色【" + role.getName() + "】中移除用户【" + user.getName() + "】自己！");
			j.setSuccess(false);
			return j;
		}else {
			if (user.getRoleList().size() <= 1){
				j.setMsg("用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！这已经是该用户的唯一角色，不能移除。");
				j.setSuccess(false);
				return j;
			}else{
				Boolean flag = systemService.outUserInRole(role, user);
				if (!flag) {
					j.setMsg( "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！");
					j.setSuccess(false);
					return j;
				}else {
					j.setMsg("用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除成功！");
					j.setSuccess(true);
					return j;
				}
			}		
		}
	}
	
	/**
	 * 角色分配
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:role:assign")
	@RequestMapping(value = "assignrole")
	public AjaxJson assignRole(Role role, String []ids) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		StringBuilder msg = new StringBuilder();
		int newNum = 0;
		for (int i = 0; i < ids.length; i++) {
			User user = systemService.assignUserToRole(role, systemService.getUser(ids[i]));
			if (null != user) {
				msg.append("<br/>新增用户【" + user.getName() + "】到角色【" + role.getName() + "】！");
				newNum++;
			}
		}
		j.setSuccess(true);
		j.setMsg("已成功分配 "+newNum+" 个用户"+msg);
		return j;
	}

	/**
	 * 验证角色名是否有效
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkName")
	public String checkName(String oldName, String name) {
		if (name!=null && name.equals(oldName)) {
			return "true";
		} else if (name!=null && systemService.getRoleByName(name) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 验证角色英文名是否有效
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkEnname")
	public String checkEnname(String oldEnname, String enname) {
		if (enname!=null && enname.equals(oldEnname)) {
			return "true";
		} else if (enname!=null && systemService.getRoleByEnname(enname) == null) {
			return "true";
		}
		return "false";
	}

}
