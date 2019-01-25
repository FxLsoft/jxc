/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.iim.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeeplus.common.json.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.iim.entity.LayGroup;
import com.jeeplus.modules.iim.entity.LayGroupUser;
import com.jeeplus.modules.iim.mapper.LayGroupUserMapper;
import com.jeeplus.modules.iim.service.LayGroupService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.UserMapper;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 群组Controller
 * @author lgf
 * @version 2016-08-07
 */
@Controller
@RequestMapping(value = "${adminPath}/iim/layGroup")
public class LayGroupController extends BaseController {

	@Autowired
	private LayGroupService layGroupService;
	
	@Autowired
	private LayGroupUserMapper layGroupUserMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@ModelAttribute
	public LayGroup get(@RequestParam(required=false) String id) {
		LayGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = layGroupService.get(id);
		}
		if (entity == null){
			entity = new LayGroup();
		}
		return entity;
	}
	
	/**
	 * 群组列表页面
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public List<LayGroup> data(LayGroup layGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		//用户自己创建的群组和属于的群组
		List<LayGroup> layGroupList = new ArrayList<LayGroup>();
		//查找我自己创建的群组
		layGroup .setCreateBy(UserUtils.getUser());
		List<LayGroup> ownerLayGroupList = layGroupService.findList(layGroup);
		
		//查找我属于的群组
		List<LayGroup> memberLayGroupList = layGroupService.findGroupList(UserUtils.getUser());
		
		layGroupList.addAll(ownerLayGroupList);
		layGroupList.addAll(memberLayGroupList);
		return layGroupList;
	}

	/**
	 * 查看，增加，编辑群组表单页面
	 */
	@RequestMapping(value = "form")
	public String form(LayGroup layGroup, Model model) {
		model.addAttribute("layGroup", layGroup);
		return "modules/iim/chat/layGroupForm";
	}

	/**
	 * 查看，增加，编辑群组成员页面
	 */
	@RequestMapping(value = "member-edit")
	public String memberEditForm(LayGroup layGroup, Model model) {
		model.addAttribute("layGroup", layGroup);
		return "modules/iim/chat/layGroupMember-edit";
	}
	@RequestMapping(value = "member-view")
	public String memberViewForm(LayGroup layGroup, Model model) {
		model.addAttribute("layGroup", layGroup);
		return "modules/iim/chat/layGroupMember-view";
	}
	@ResponseBody
	@RequestMapping(value = "memberData")
	public List<LayGroupUser> memberData(LayGroup layGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		return layGroupService.getUsersByGroup(layGroup);
	}
	/**
	 * 添加群组成员--->常用联系人
	 */
	@ResponseBody
	@RequestMapping(value = "addUser")
	public AjaxJson addUser(String ids, String groupid, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			User user = userMapper.get(id);
			LayGroup group =layGroupService.get(groupid);
			LayGroupUser lgUser = new LayGroupUser();
			lgUser.setGroup(group);
			lgUser.setUser(user);
			
			 if(layGroupUserMapper.findList(lgUser).size() == 0 && !user.getId().equals(group.getCreateBy().getId())){
				 lgUser.setId(IdGen.uuid());
				 layGroupUserMapper.insert(lgUser);
			 }
		}
		j.setMsg("添加组员成功");
		return j;
	}
	/**
	 * 保存群组
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(LayGroup layGroup, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
        layGroupService.save(layGroup);//保存
		j.setMsg("保存群组成功");
		return j;
	}
	
	/**
	 * 删除群组
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(LayGroup layGroup, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		layGroupService.delete(layGroup);
		j.setMsg("解散群组成功!");
		return j;
	}
	
	/**
	 * 退出群组
	 */
	@ResponseBody
	@RequestMapping(value = "logout")
	public AjaxJson logout(LayGroupUser layGroupUser, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		layGroupUserMapper.delete(layGroupUserMapper.findList(layGroupUser).get(0));
		j.setMsg("退出群组成功!");
		return j;
	}

}