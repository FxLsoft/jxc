/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.activiti.web;

import java.util.List;
import java.util.Map;


import com.google.common.collect.Maps;
import org.activiti.engine.repository.ProcessDefinition;
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

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.act.service.ActProcessService;
import com.jeeplus.modules.act.service.ActTaskService;
import com.jeeplus.modules.test.activiti.entity.OALeave;
import com.jeeplus.modules.test.activiti.service.OALeaveService;

/**
 * 请假申请Controller
 * @author 刘高峰
 * @version 2018-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/test/activiti/oALeave")
public class OALeaveController extends BaseController {

	@Autowired
	private OALeaveService oALeaveService;
	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private ActTaskService actTaskService;
	
	@ModelAttribute
	public OALeave get(@RequestParam(required=false) String id) {
		OALeave entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oALeaveService.get(id);
		}
		if (entity == null){
			entity = new OALeave();
		}
		return entity;
	}
	

	/**
	 * 查看，增加，编辑请假申请表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, OALeave oALeave, Model model) {
		model.addAttribute("oALeave", oALeave);
		if("add".equals(mode) || "edit".equals(mode)){
			return "modules/test/activiti/oALeaveForm";
		}else{//audit
			return "modules/test/activiti/oALeaveAudit";
		}
	}

	/**
	 * 保存请假申请
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(OALeave oALeave, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(oALeave);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}


		/**
		 * 流程审批
		 */
		if (StringUtils.isBlank(oALeave.getId())){
			//新增或编辑表单保存
			oALeaveService.save(oALeave);//保存
			// 启动流程
			ProcessDefinition p = actProcessService.getProcessDefinition(oALeave.getAct().getProcDefId());
			String title = oALeave.getCurrentUser().getName()+"在"+ DateUtils.getDateTime()+"发起"+p.getName();
			actTaskService.startProcess(p.getKey(),  "test_activiti_leave", oALeave.getId(), title);
			j.setMsg("发起流程审批成功!");
			j.getBody().put("targetUrl",  "/act/task/process/");
		}else{
			//新增或编辑表单保存
			oALeaveService.save(oALeave);//保存
			oALeave.getAct().setComment(("yes".equals(oALeave.getAct().getFlag())?"[重新申请] ":"[销毁申请] "));
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("reapply", "yes".equals(oALeave.getAct().getFlag())? true : false);
			actTaskService.complete(oALeave.getAct().getTaskId(), oALeave.getAct().getProcInsId(), oALeave.getAct().getComment(), oALeave.getContent(), vars);
			j.setMsg("提交成功！");
			j.getBody().put("targetUrl",  "/act/task/todo/");
		}

		return j;
	}
	


}