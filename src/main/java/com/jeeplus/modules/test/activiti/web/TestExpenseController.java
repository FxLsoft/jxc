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
import com.jeeplus.modules.test.activiti.entity.TestExpense;
import com.jeeplus.modules.test.activiti.service.TestExpenseService;

/**
 * 报销申请Controller
 * @author 刘高峰
 * @version 2018-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/test/activiti/testExpense")
public class TestExpenseController extends BaseController {

	@Autowired
	private TestExpenseService testExpenseService;
	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private ActTaskService actTaskService;
	
	@ModelAttribute
	public TestExpense get(@RequestParam(required=false) String id) {
		TestExpense entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testExpenseService.get(id);
		}
		if (entity == null){
			entity = new TestExpense();
		}
		return entity;
	}
	

	/**
	 * 查看，增加，编辑报销申请表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, TestExpense testExpense, Model model) {
		model.addAttribute("testExpense", testExpense);
		if("add".equals(mode) || "edit".equals(mode)){
			return "modules/test/activiti/testExpenseForm";
		}else{//audit
			return "modules/test/activiti/testExpenseAudit";
		}
	}

	/**
	 * 保存报销申请
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(TestExpense testExpense, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(testExpense);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}


		/**
		 * 流程审批
		 */
		if (StringUtils.isBlank(testExpense.getId())){
			//新增或编辑表单保存
			testExpenseService.save(testExpense);//保存
			// 启动流程
			ProcessDefinition p = actProcessService.getProcessDefinition(testExpense.getAct().getProcDefId());
			String title = testExpense.getCurrentUser().getName()+"在"+ DateUtils.getDateTime()+"发起"+p.getName();
			actTaskService.startProcess(p.getKey(),  "test_activiti_expense", testExpense.getId(), title);
			j.setMsg("发起流程审批成功!");
			j.getBody().put("targetUrl",  "/act/task/process/");
		}else{
			//新增或编辑表单保存
			testExpenseService.save(testExpense);//保存
			testExpense.getAct().setComment(("yes".equals(testExpense.getAct().getFlag())?"[重新申请] ":"[销毁申请] "));
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("reapply", "yes".equals(testExpense.getAct().getFlag())? true : false);
			actTaskService.complete(testExpense.getAct().getTaskId(), testExpense.getAct().getProcInsId(), testExpense.getAct().getComment(), testExpense.getContent(), vars);
			j.setMsg("提交成功！");
			j.getBody().put("targetUrl",  "/act/task/todo/");
		}

		return j;
	}
	


}