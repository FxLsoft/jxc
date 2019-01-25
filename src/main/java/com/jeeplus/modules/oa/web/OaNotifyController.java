/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oa.web;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandler;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.oa.service.OaNotifyService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 通知通告Controller
 * @author jeeplus
 * @version 2017-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaNotify")
public class OaNotifyController extends BaseController {

	@Autowired
	private OaNotifyService oaNotifyService;
	
	@ModelAttribute
	public OaNotify get(@RequestParam(required=false) String id) {
		OaNotify entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaNotifyService.get(id);
		}
		if (entity == null){
			entity = new OaNotify();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaNotify:list")
	@RequestMapping(value = {"list", ""})
	public String list(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("isSelf", false);
		return "modules/oa/notify/oaNotifyList";
	}
	
	/**
	 * 通告列表数据
	 */
	@ResponseBody
	@RequiresPermissions("oa:oaNotify:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OaNotify oaNotify, boolean isSelf, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(isSelf){
			oaNotify.setSelf(true);
		}
		Page<OaNotify> page = oaNotifyService.findPage(new Page<OaNotify>(request, response), oaNotify); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑报告表单页面
	 */
	@RequiresPermissions(value={"oa:oaNotify:view","oa:oaNotify:add","oa:oaNotify:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OaNotify oaNotify, boolean isSelf, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			if(isSelf){
				oaNotifyService.updateReadFlag(oaNotify);
				oaNotify = oaNotifyService.get(oaNotify.getId());
			}
			oaNotify = oaNotifyService.getRecordList(oaNotify);
		}
		model.addAttribute("isSelf", isSelf);
		model.addAttribute("oaNotify", oaNotify);
		return "modules/oa/notify/oaNotifyForm";
	}

	@ResponseBody
	@RequiresPermissions(value={"oa:oaNotify:add","oa:oaNotify:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(OaNotify oaNotify, Model model, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(oaNotify);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		// 如果是修改，则状态为已发布，则不能再进行操作
		if (StringUtils.isNotBlank(oaNotify.getId())){
			OaNotify e = oaNotifyService.get(oaNotify.getId());
			if ("1".equals(e.getStatus())){
				j.setSuccess(false);
				j.setMsg("已发布，不能操作！");
				return j;
			}
		}
		oaNotifyService.save(oaNotify);
		if("1".equals(oaNotify.getStatus())){
			List<OaNotifyRecord> list = oaNotify.getOaNotifyRecordList();
			for(OaNotifyRecord o : list){
				//发送通知到客户端
//				ServletContext context = SpringContextHolder.getBean(ServletContext.class);
				new SystemInfoSocketHandler().sendMessageToUser(UserUtils.get(o.getUser().getId()).getLoginName(), "收到一条新通知，请到我的通知查看！");
			}
		}
		j.setMsg("保存通知'" + oaNotify.getTitle() + "'成功");
		return j;
	}

	@ResponseBody
	@RequiresPermissions("oa:oaNotify:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OaNotify oaNotify, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		oaNotifyService.delete(oaNotify);
		j.setMsg("删除通知成功");
		return j;
	}

	@ResponseBody
	@RequiresPermissions("oa:oaNotify:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			oaNotifyService.delete(oaNotifyService.get(id));
		}
		j.setMsg("删除通知成功");
		return j;
	}
	
	/**
	 * 我的通知列表
	 */
	@RequestMapping(value = "self")
	public String selfList(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("isSelf", true);
		return "modules/oa/notify/oaNotifyList";
	}

	


	/**
	 * 查看我的通知-数据
	 */
	@RequestMapping(value = "viewData")
	@ResponseBody
	public OaNotify viewData(OaNotify oaNotify, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			oaNotifyService.updateReadFlag(oaNotify);
			return oaNotify;
		}
		return null;
	}
	
	/**
	 * 查看我的通知-发送记录
	 */
	@RequestMapping(value = "viewRecordData")
	@ResponseBody
	public OaNotify viewRecordData(OaNotify oaNotify, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			oaNotify = oaNotifyService.getRecordList(oaNotify);
			return oaNotify;
		}
		return null;
	}
	
	/**
	 * 获取我的通知数目
	 */
	@RequestMapping(value = "self/count")
	@ResponseBody
	public String selfCount(OaNotify oaNotify, Model model) {
		oaNotify.setSelf(true);
		oaNotify.setReadFlag("0");
		return String.valueOf(oaNotifyService.findCount(oaNotify));
	}
}