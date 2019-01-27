/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.act.service;

import java.io.InputStream;
import java.util.*;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.BaseService;
import com.jeeplus.modules.act.entity.Act;
import com.jeeplus.modules.act.mapper.ActMapper;
import com.jeeplus.modules.act.utils.ActUtils;
import com.jeeplus.modules.act.utils.ProcessDefCache;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 流程定义相关Service
 * @author jeeplus
 * @version 2016-11-03
 */
@Service
@Transactional(readOnly = true)
public class ActTaskService extends BaseService {

	@Autowired
	private ActMapper actMapper;
	
	@Autowired
	private ProcessEngineFactoryBean processEngine;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;
	
	/**
	 * 获取待办任务列表
	 * @return
	 */
	public Page<HashMap<String,String>>  todoList(Page<HashMap<String,String>> page, Act act){
		List<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());



		// =============== 已经签收的任务  ===============
		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
				.includeProcessVariables().orderByTaskCreateTime().desc();
		
		// 设置查询条件
		if (StringUtils.isNotBlank(act.getProcDefKey())){
			todoTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getBeginDate() != null){
			todoTaskQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			todoTaskQuery.taskCreatedBefore(act.getEndDate());
		}


		// =============== 等待签收的任务  ===============
		TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId)
				.includeProcessVariables().active().orderByTaskCreateTime().desc();

		// 设置查询条件
		if (StringUtils.isNotBlank(act.getProcDefKey())){
			toClaimQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getBeginDate() != null){
			toClaimQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			toClaimQuery.taskCreatedBefore(act.getEndDate());
		}

		long taskCount = todoTaskQuery.count();
		long claimCount = toClaimQuery.count();
		long total = taskCount + claimCount;
		page.setCount(total);


		int start = page.getFirstResult();
		int end = page.getFirstResult() + page.getMaxResults();
		// 查询列表
		List<Task> todoList = Lists.newArrayList();
		// 查询列表
		List<Task> toClaimList = Lists.newArrayList();
		if(end == -1){//不分页
			todoList = todoTaskQuery.list();
			toClaimList = toClaimQuery.list();
		}else{

			if(end <= taskCount){
				todoList = todoTaskQuery.listPage(start, page.getMaxResults());
			}else if(start <taskCount){
				todoList = todoTaskQuery.listPage(start, (int)taskCount - start);
				toClaimList = toClaimQuery.listPage(0, end -(int)taskCount);
			}else{
				toClaimList= toClaimQuery.listPage(start-(int)taskCount, page.getMaxResults());
			}
		}



		for (Task task : todoList) {
			HashMap map = new HashMap();
			map.put("task.assignee",task.getAssignee());
			map.put("task.id", task.getId());
			map.put("task.createTime", task.getCreateTime());
			map.put("task.name", task.getName());
			map.put("task.executionId",task.getExecutionId());
			map.put("task.processDefinitionId", task.getProcessDefinitionId());
			map.put("task.processInstanceId", task.getProcessInstanceId());
			map.put("task.taskDefinitionKey", task.getTaskDefinitionKey());
			map.put("vars",task.getProcessVariables());
			map.put("procDef.name", ProcessDefCache.get(task.getProcessDefinitionId()).getName());
			map.put("procDef.version", ProcessDefCache.get(task.getProcessDefinitionId()).getVersion());
			map.put("status","todo");
			page.getList().add(map);
		}
		

		for (Task task : toClaimList) {
			HashMap map = new HashMap();
			map.put("task.assignee",task.getAssignee());
			map.put("task.id", task.getId());
			map.put("task.name", task.getName());
			map.put("task.createTime", task.getCreateTime());
			map.put("task.executionId",task.getExecutionId());
			map.put("task.processInstanceId", task.getProcessInstanceId());
			map.put("task.processDefinitionId", task.getProcessDefinitionId());
			map.put("task.taskDefinitionKey", task.getTaskDefinitionKey());
			map.put("vars",task.getProcessVariables());
			map.put("procDef.name", ProcessDefCache.get(task.getProcessDefinitionId()).getName());
			map.put("procDef.version", ProcessDefCache.get(task.getProcessDefinitionId()).getVersion());
			map.put("status", "claim");
			page.getList().add(map);
		}

		return page;

	}
	
	/**
	 * 获取已办任务列表
	 * @param page
	 * @return
	 */
	public Page<HashMap<String,String>> historicList(Page<HashMap<String,String>> page, Act act){
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());

		HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).finished()
				.includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();
		
		// 设置查询条件
		if (StringUtils.isNotBlank(act.getProcDefKey())){
			histTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getBeginDate() != null){
			histTaskQuery.taskCompletedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			histTaskQuery.taskCompletedBefore(act.getEndDate());
		}
		
		// 查询总数
		page.setCount(histTaskQuery.count());
		page.initialize();
		
		// 查询列表
		List<HistoricTaskInstance> histList = Lists.newArrayList();
		if(page.getMaxResults() == -1){
			histList = histTaskQuery.list();
		}else{
			histList = histTaskQuery.listPage(page.getFirstResult(), page.getMaxResults());
		}
		for (HistoricTaskInstance histTask : histList) {
			Act e = new Act();
			e.setHistTask(histTask);
			e.setVars(histTask.getProcessVariables());
//			e.setTaskVars(histTask.getTaskLocalVariables());
//			System.out.println(histTask.getId()+"  =  "+histTask.getProcessVariables() + "  ========== " + histTask.getTaskLocalVariables());
			e.setProcDef(ProcessDefCache.get(histTask.getProcessDefinitionId()));
//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
			e.setStatus("finish");
			HashMap map = new HashMap();
			map.put("task.assignee",histTask.getAssignee());
			map.put("task.id", histTask.getId());
			map.put("task.name", histTask.getName());
			map.put("task.endTime", histTask.getEndTime());
			map.put("task.executionId",histTask.getExecutionId());
			map.put("task.processInstanceId", histTask.getProcessInstanceId());
			map.put("task.processDefinitionId", histTask.getProcessDefinitionId());
			map.put("task.taskDefinitionKey", histTask.getTaskDefinitionKey());
			map.put("vars",histTask.getProcessVariables());
			map.put("procDef.name", ProcessDefCache.get(histTask.getProcessDefinitionId()).getName());
			map.put("procDef.version", ProcessDefCache.get(histTask.getProcessDefinitionId()).getVersion());
			try {
				map.put("status", queryProcessState(histTask.getProcessInstanceId()));
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			page.getList().add(map);
		}
		return page;
	}
	
	/**
	 * 获取流转历史任务列表
	 * @param procInsId 流程实例
	 * @param startAct 开始活动节点名称
	 * @param endAct 结束活动节点名称
	 */
	public List<Act> histoicFlowList(String procInsId, String startAct, String endAct){
		List<Act> actList = Lists.newArrayList();
		List<HistoricActivityInstance> list = Lists.newArrayList();
		List<HistoricActivityInstance> historicActivityInstances2 = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInsId)
				.orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();;
		for(HistoricActivityInstance historicActivityInstance:historicActivityInstances2){
			if(historicActivityInstance.getEndTime() != null){
				list.add(historicActivityInstance);
			}
		}

		for(HistoricActivityInstance historicActivityInstance:historicActivityInstances2){
			if(historicActivityInstance.getEndTime() == null){
				list.add(historicActivityInstance);
			}
		}
		boolean start = false;
		Map<String, Integer> actMap = Maps.newHashMap();
		
		for (int i=0; i<list.size(); i++){
			
			HistoricActivityInstance histIns = list.get(i);
			
			// 过滤开始节点前的节点
			if (StringUtils.isNotBlank(startAct) && startAct.equals(histIns.getActivityId())){
				start = true;
			}
			if (StringUtils.isNotBlank(startAct) && !start){
				continue;
			}
			
			// 只显示开始节点和结束节点，并且执行人不为空的任务
			if (StringUtils.isNotBlank(histIns.getAssignee())
					 || "startEvent".equals(histIns.getActivityType())
					 || "endEvent".equals(histIns.getActivityType())){
				
				// 给节点增加一个序号
				Integer actNum = actMap.get(histIns.getActivityId());
				if (actNum == null){
					actMap.put(histIns.getActivityId(), actMap.size());
				}
				
				Act e = new Act();
				e.setHistIns(histIns);
				// 获取流程发起人名称
				if ("startEvent".equals(histIns.getActivityType())){
					List<HistoricProcessInstance> il = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInsId).orderByProcessInstanceStartTime().asc().list();
//					List<HistoricIdentityLink> il = historyService.getHistoricIdentityLinksForProcessInstance(procInsId);
					if (il.size() > 0){
						if (StringUtils.isNotBlank(il.get(0).getStartUserId())){
							User user = UserUtils.getByLoginName(il.get(0).getStartUserId());
							if (user != null){
								e.setAssignee(histIns.getAssignee());
								e.setAssigneeName(user.getName());
							}
						}
					}
				}
				// 获取任务执行人名称
				if (StringUtils.isNotEmpty(histIns.getAssignee())){
					User user = UserUtils.getByLoginName(histIns.getAssignee());
					if (user != null){
						e.setAssignee(histIns.getAssignee());
						e.setAssigneeName(user.getName());
					}
				}
				// 获取意见评论内容
				if (StringUtils.isNotBlank(histIns.getTaskId())){
					List<Comment> commentList = taskService.getTaskComments(histIns.getTaskId());
					if (commentList.size()>0){
						e.setComment(commentList.get(0).getFullMessage());
					}
				}
				actList.add(e);
			}
			
			// 过滤结束节点后的节点
			if (StringUtils.isNotBlank(endAct) && endAct.equals(histIns.getActivityId())){
				boolean bl = false;
				Integer actNum = actMap.get(histIns.getActivityId());
				// 该活动节点，后续节点是否在结束节点之前，在后续节点中是否存在
				for (int j=i+1; j<list.size(); j++){
					HistoricActivityInstance hi = list.get(j);
					Integer actNumA = actMap.get(hi.getActivityId());
					if ((actNumA != null && actNumA < actNum) || StringUtils.equals(hi.getActivityId(), histIns.getActivityId())){
						bl = true;
					}
				}
				if (!bl){
					break;
				}
			}
		}
		return actList;
	}

	/**
	 * 获取流程定义列表
	 * @param category 流程分类
	 */
	public Page<Object[]> processList(Page<Object[]> page, String category) {
		/*
		 * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		 */
	    ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
	    		.latestVersion().active().orderByProcessDefinitionKey().asc();
	    
	    if (StringUtils.isNotEmpty(category)){
	    	processDefinitionQuery.processDefinitionCategory(category);
		}
	    
	    page.setCount(processDefinitionQuery.count());
		List<ProcessDefinition> processDefinitionList = Lists.newArrayList();
		if(page.getMaxResults() == -1){//不分页
			processDefinitionList = processDefinitionQuery.list();
		}else{
			processDefinitionList = processDefinitionQuery.listPage(page.getFirstResult(), page.getMaxResults());
		}
	    for (ProcessDefinition processDefinition : processDefinitionList) {
	      String deploymentId = processDefinition.getDeploymentId();
	      Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
	      page.getList().add(new Object[]{processDefinition, deployment});
	    }
		return page;
	}
	
	/**
	 * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
	 * @return
	 */
	public String getFormKey(String procDefId, String taskDefKey){
		String formKey = "";
		if (StringUtils.isNotBlank(procDefId)){
			if (StringUtils.isNotBlank(taskDefKey)){
				try{
					formKey = formService.getTaskFormKey(procDefId, taskDefKey);
				}catch (Exception e) {
					formKey = "";
				}
			}
			if (StringUtils.isBlank(formKey)){
				formKey = formService.getStartFormKey(procDefId);
			}
			if (StringUtils.isBlank(formKey)){
				formKey = "/404";
			}
		}
		logger.debug("getFormKey: {}", formKey);
		return formKey;
	}
	
	/**
	 * 获取正在运行的流程实例对象
	 * @param procInsId
	 * @return
	 */
	@Transactional(readOnly = false)
	public ProcessInstance getProcIns(String procInsId) {
		return runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
	}
	
	/**
	 * 获取已经结束流程实例对象
	 * @param procInsId
	 * @return
	 */
	@Transactional(readOnly = false)
	public HistoricProcessInstance getFinishedProcIns(String procInsId) {
		return historyService.createHistoricProcessInstanceQuery().processInstanceId(procInsId).singleResult();
	}
	
	
	/**
	 * 获取正在运行的流程实例对象列表
	 * @param procDefKey
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<ProcessInstance> getRunngingProcIns(String procDefKey,  User user, int[] pageParams) {
		ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processDefinitionKey(procDefKey).active().orderByProcessInstanceId().desc();
		List<ProcessInstance> list = new ArrayList<ProcessInstance>();
		if (User.isAdmin(user.getId())){
			list = query.listPage(pageParams[0], pageParams[1]);
        } else {
        	list = query.involvedUser(user.getLoginName()).listPage(pageParams[0], pageParams[1]);
        }
		return list;
	}
	

	/**
	 * 获取已经结束的流程实例对象列表
	 * @param procDefKey
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<HistoricProcessInstance> getFinishedProcIns(String procDefKey, User user, int[] pageParams) {
		HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(procDefKey).finished().orderByProcessInstanceEndTime().desc();
		List<HistoricProcessInstance> list = new ArrayList<HistoricProcessInstance>();
		if (User.isAdmin(user.getId())){
			list = query.listPage(pageParams[0], pageParams[1]);
		} else {
			list = query.involvedUser(user.getLoginName()).listPage(pageParams[0], pageParams[1]);
		}
		return list;
	}

	/**
	 * 获取我发起的流程申请列表
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = false)
	public Page<HashMap>  getMyStartedProcIns( User user, Page<HashMap> page) throws  Exception{
		HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().startedBy(user.getLoginName()).includeProcessVariables().orderByProcessInstanceStartTime().desc();
		page.setCount(query.count());
		List<HistoricProcessInstance> histList = Lists.newArrayList();
		if(page.getMaxResults() == -1){//不分页
			histList = query.list();
		}else {
			histList = query.involvedUser(user.getLoginName()).listPage(page.getFirstResult(), page.getMaxResults());
		}
		for (HistoricProcessInstance historicProcessInstance : histList) {

			HashMap map = new HashMap();
//			p.getActivityId());
//			map.put("suspended", String.valueOf(p.isSuspended()));
//			historicProcessInstance.
			String state = queryProcessState(historicProcessInstance.getId());
			map.put("procIns.endTime", historicProcessInstance.getEndTime());
			map.put("procIns.startTime",historicProcessInstance.getStartTime());
			map.put("procIns.processInstanceId", historicProcessInstance.getId());
			map.put("procDef.processDefinitionId", historicProcessInstance.getProcessDefinitionId());
			map.put("vars",historicProcessInstance.getProcessVariables());
			map.put("procDef.name", historicProcessInstance.getProcessDefinitionName());
			map.put("procDef.version", historicProcessInstance.getProcessDefinitionVersion());
			map.put("procIns.status", state);
			page.getList().add(map);
		}

		return page;
	}
	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(String procDefKey, String businessTable, String businessId) {
		return startProcess(procDefKey, businessTable, businessId, "");
	}
	
	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @param title			流程标题，显示在待办任务标题
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(String procDefKey, String businessTable, String businessId, String title) {
		Map<String, Object> vars = Maps.newHashMap();
		return startProcess(procDefKey, businessTable, businessId, title, vars);
	}
	
	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars			流程变量
	 * @return 流程实例ID
	 */
	@SuppressWarnings("unused")
	@Transactional(readOnly = false)
	public String startProcess(String procDefKey, String businessTable, String businessId, String title, Map<String, Object> vars) {
		//String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId())
		// 设置流程变量
		if (vars == null){
			vars = Maps.newHashMap();
		}

		String userId = (String) vars.get("applyUserId");
		if(userId == null ){
			 userId = UserUtils.getUser().getLoginName();
		}
		String userName = UserUtils.getByLoginName(userId).getName();
		vars.put("userName", userName);

		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(userId);
		
		// 设置流程标题
		if (StringUtils.isNotBlank(title)){
			vars.put("title", title);
		}
		
		// 启动流程
		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, businessTable+":"+businessId, vars);
		
		// 更新业务表流程实例ID
		Act act = new Act();
		act.setBusinessTable(businessTable);// 业务表名
		act.setBusinessId(businessId);	// 业务表ID
		act.setProcInsId(procIns.getId());
		act.setVars(vars);
		actMapper.updateProcInsIdByBusinessId(act);
		return act.getProcInsId();
	}

	/**
	 * 获取任务
	 * @param taskId 任务ID
	 */
	public Task getTask(String taskId){
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}
	
	/**
	 * 删除任务
	 * @param taskId 任务ID
	 * @param deleteReason 删除原因
	 */
	public void deleteTask(String taskId, String deleteReason){
		taskService.deleteTask(taskId, deleteReason);
	}
	
	/**
	 * 签收任务
	 * @param taskId 任务ID
	 * @param userId 签收用户ID（用户登录名）
	 */
	@Transactional(readOnly = false)
	public void claim(String taskId, String userId){
		taskService.claim(taskId, userId);
	}
	
	/**
	 * 提交任务, 并保存意见
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param vars 任务变量
	 */
	@Transactional(readOnly = false)
	public void complete(String taskId, String procInsId, String comment, Map<String, Object> vars){
		complete(taskId, procInsId, comment, "", vars);
	}
	
	/**
	 * 提交任务, 并保存意见
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars 任务变量
	 */
	@Transactional(readOnly = false)
	public void complete(String taskId, String procInsId, String comment, String title, Map<String, Object> vars){
		// 添加意见
		if (StringUtils.isNotBlank(procInsId) && StringUtils.isNotBlank(comment)){
			taskService.addComment(taskId, procInsId, comment);
		}
		
		// 设置流程变量
		if (vars == null){
			vars = Maps.newHashMap();
		}
		
		// 设置流程标题
		if (StringUtils.isNotBlank(title)){
			vars.put("title", title);
		}
		
		// 提交任务
		taskService.complete(taskId, vars);
	}

	/**
	 * 完成第一个任务
	 * @param procInsId
	 */
	public void completeFirstTask(String procInsId){
		completeFirstTask(procInsId, null, null, null);
	}
	
	/**
	 * 完成第一个任务
	 * @param procInsId
	 * @param comment
	 * @param title
	 * @param vars
	 */
	public void completeFirstTask(String procInsId, String comment, String title, Map<String, Object> vars){
		String userId = UserUtils.getUser().getLoginName();
		Task task = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(procInsId).active().singleResult();
		if (task != null){
			complete(task.getId(), procInsId, comment, title, vars);
		}
	}
	
	/**
	 * 查询流程实例状态
	 * @param processInstanceId
	 * @return
	 * @throws Exception
	 */
	public String queryProcessState(String processInstanceId) throws Exception {
		// 通过流程实例ID查询流程实例
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (pi != null) {
			if(pi.isSuspended()){
				//执行实例
				return "[已挂起]";
			}else{
				//执行实例
				List<Task> tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).active().orderByTaskCreateTime().desc().listPage(0, 1);
				return "[进行中] :" + tasks.get(0).getName();
			}

		} else {
			HistoricProcessInstance pi2 = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			if(pi2 != null){
				if(pi2.getDeleteReason() == null){
					return "[正常结束]";
				}else if(pi2.getDeleteReason().equals("用户撤销")){
					return "[用户撤销]";
				}else{
					return "[流程作废] :"+	pi2.getDeleteReason();
				}

			}else{
				return "[已删除]";
			}

		}
	}
	
	/** 
     * 终止流程实例 
     * @param processInstanceId
     */  
    public void endProcessInstance(String processInstanceId, String deleteReason) throws Exception {  
    	runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }
	

//	/**
//	 * 委派任务
//	 * @param taskId 任务ID
//	 * @param userId 被委派人
//	 */
//	public void delegateTask(String taskId, String userId){
//		taskService.delegateTask(taskId, userId);
//	}
//	
//	/**
//	 * 被委派人完成任务
//	 * @param taskId 任务ID
//	 */
//	public void resolveTask(String taskId){
//		taskService.resolveTask(taskId);
//	}
//	
//	/**
//	 * 回退任务
//	 * @param taskId
//	 */
//	public void backTask(String taskId){
//		taskService.
//	}
	
	////////////////////////////////////////////////////////////////////
	
	/**
	 * 读取带跟踪的图片
	 * @param executionId	环节ID
	 * @return	封装了各种节点信息
	 */
	public InputStream tracePhoto(String processDefinitionId, String executionId) {
		// ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		
		List<String> activeActivityIds = Lists.newArrayList();
		if (runtimeService.createExecutionQuery().executionId(executionId).count() > 0){
			activeActivityIds = runtimeService.getActiveActivityIds(executionId);
		}
		
		List<String> highLightedFlows  = Lists.newArrayList();	    
	    // 获得历史活动记录实体（通过启动时间正序排序，不然有的线可以绘制不出来）
		List<HistoricActivityInstance> historicActivityInstances= Lists.newArrayList();
	    List<HistoricActivityInstance> historicActivityInstances2 = historyService
				.createHistoricActivityInstanceQuery().executionId(executionId).orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();
	    for(HistoricActivityInstance historicActivityInstance:historicActivityInstances2){
	    	if(historicActivityInstance.getEndTime() != null){
	    		historicActivityInstances.add(historicActivityInstance);
			}
		}

		for(HistoricActivityInstance historicActivityInstance:historicActivityInstances2){
			if(historicActivityInstance.getEndTime() == null){
				historicActivityInstances.add(historicActivityInstance);
			}
		}

		List<String> highLightedActivities = Lists.newArrayList();
		for(HistoricActivityInstance historicActivityInstance : historicActivityInstances){
			highLightedActivities.add(historicActivityInstance.getActivityId());
		}
	    // 计算活动线  
		highLightedFlows = this.getHighLightedFlows((ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
								.getDeployedProcessDefinition(processDefinitionId),historicActivityInstances);
	    
		// 不使用spring请使用下面的两行代码
	//	 ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl)ProcessEngines.getDefaultProcessEngine();
	//	 Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());

		// 使用spring注入引擎请使用下面的这行代码
		Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());

		DefaultProcessDiagramGenerator processDiagramGeneratornew = new DefaultProcessDiagramGenerator();
		//return processDiagramGeneratornew.generateDiagram(bpmnModel, "png", activeActivityIds);
		return processDiagramGeneratornew.generateDiagram(bpmnModel, "png",
				highLightedActivities, highLightedFlows,
				processEngine.getProcessEngineConfiguration().getActivityFontName(),
				processEngine.getProcessEngineConfiguration().getLabelFontName(), 
				executionId, processEngine.getProcessEngineConfiguration().getClassLoader(), 1.0);
	}
	
	/**
	 * 获得高亮线
	 * 
	 * @param processDefinitionEntity 流程定义实体
	 * @param historicActivityInstances 历史活动实体
	 * @return 线ID集合
	 */
	public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,List<HistoricActivityInstance> historicActivityInstances) {
		List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
		for (int i = 0; i < historicActivityInstances.size(); i++) {// 对历史流程节点进行遍历
			ActivityImpl activityImpl1 = processDefinitionEntity
					.findActivity(historicActivityInstances.get(i)
							.getActivityId());// 得到节点定义的详细信息
			List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
			HistoricActivityInstance historicActivityInstance1 = historicActivityInstances
					.get(i);// 后续第一个节点
			for (int j = 0; j < historicActivityInstances.size(); j++) {
				if(i == j){
					continue;
				}
				HistoricActivityInstance historicActivityInstance2 = historicActivityInstances
						.get(j);// 后续第一个节点
				if (historicActivityInstance1.getEndTime()!=null && historicActivityInstance1.getEndTime().equals(
						historicActivityInstance2.getStartTime()) ) {
					// 如果第一个节点的结束时间和第二个节点开始时间相同保存
					ActivityImpl sameActivityImpl2 = processDefinitionEntity
							.findActivity(historicActivityInstance2.getActivityId());
					sameStartTimeNodes.add(sameActivityImpl2);
				}
			}
			List<PvmTransition> pvmTransitions = activityImpl1
					.getOutgoingTransitions();// 取出节点的所有出去的线
			for (PvmTransition pvmTransition : pvmTransitions) {
				// 对所有的线进行遍历
				ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
						.getDestination();
				// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
			if (sameStartTimeNodes.contains(pvmActivityImpl)) {
				highFlows.add(pvmTransition.getId());
			}
			}

		}
		return highFlows;
	}
	
	/**
	 * 流程跟踪图信息
	 * @param processInstanceId		流程实例ID
	 * @return	封装了各种节点信息
	 */
	public List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception {
		Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();//执行实例
		Object property = PropertyUtils.getProperty(execution, "activityId");
		String activityId = "";
		if (property != null) {
			activityId = property.toString();
		}
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		List<ActivityImpl> activitiList = processDefinition.getActivities();//获得当前任务的所有节点

		List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
		for (ActivityImpl activity : activitiList) {

			boolean currentActiviti = false;
			String id = activity.getId();

			// 当前节点
			if (id.equals(activityId)) {
				currentActiviti = true;
			}

			Map<String, Object> activityImageInfo = packageSingleActivitiInfo(activity, processInstance, currentActiviti);

			activityInfos.add(activityImageInfo);
		}

		return activityInfos;
	}

	/**
	 * 封装输出信息，包括：当前节点的X、Y坐标、变量信息、任务类型、任务描述
	 * @param activity
	 * @param processInstance
	 * @param currentActiviti
	 * @return
	 */
	private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance,
			boolean currentActiviti) throws Exception {
		Map<String, Object> vars = new HashMap<String, Object>();
		Map<String, Object> activityInfo = new HashMap<String, Object>();
		activityInfo.put("currentActiviti", currentActiviti);
		setPosition(activity, activityInfo);
		setWidthAndHeight(activity, activityInfo);

		Map<String, Object> properties = activity.getProperties();
		vars.put("节点名称", properties.get("name"));
		vars.put("任务类型", ActUtils.parseToZhType(properties.get("type").toString()));

		ActivityBehavior activityBehavior = activity.getActivityBehavior();
		logger.debug("activityBehavior={}", activityBehavior);
		if (activityBehavior instanceof UserTaskActivityBehavior) {

			Task currentTask = null;

			// 当前节点的task
			if (currentActiviti) {
				currentTask = getCurrentTaskInfo(processInstance);
			}

			// 当前任务的分配角色
			UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
			TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
			Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
			if (!candidateGroupIdExpressions.isEmpty()) {

				// 任务的处理角色
				setTaskGroup(vars, candidateGroupIdExpressions);

				// 当前处理人
				if (currentTask != null) {
					setCurrentTaskAssignee(vars, currentTask);
				}
			}
		}

		vars.put("节点说明", properties.get("documentation"));

		String description = activity.getProcessDefinition().getDescription();
		vars.put("描述", description);

		logger.debug("trace variables: {}", vars);
		activityInfo.put("vars", vars);
		return activityInfo;
	}

	/**
	 * 设置任务组
	 * @param vars
	 * @param candidateGroupIdExpressions
	 */
	private void setTaskGroup(Map<String, Object> vars, Set<Expression> candidateGroupIdExpressions) {
		String roles = "";
		for (Expression expression : candidateGroupIdExpressions) {
			String expressionText = expression.getExpressionText();
			String roleName = identityService.createGroupQuery().groupId(expressionText).singleResult().getName();
			roles += roleName;
		}
		vars.put("任务所属角色", roles);
	}

	/**
	 * 设置当前处理人信息
	 * @param vars
	 * @param currentTask
	 */
	private void setCurrentTaskAssignee(Map<String, Object> vars, Task currentTask) {
		String assignee = currentTask.getAssignee();
		if (assignee != null) {
			org.activiti.engine.identity.User assigneeUser = identityService.createUserQuery().userId(assignee).singleResult();
			String userInfo = assigneeUser.getFirstName() + " " + assigneeUser.getLastName();
			vars.put("当前处理人", userInfo);
		}
	}

	/**
	 * 获取当前节点信息
	 * @param processInstance
	 * @return
	 */
	private Task getCurrentTaskInfo(ProcessInstance processInstance) {
		Task currentTask = null;
		try {
			String activitiId = (String) PropertyUtils.getProperty(processInstance, "activityId");
			logger.debug("current activity id: {}", activitiId);

			currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskDefinitionKey(activitiId)
					.singleResult();
			logger.debug("current task for processInstance: {}", ToStringBuilder.reflectionToString(currentTask));

		} catch (Exception e) {
			logger.error("can not get property activityId from processInstance: {}", processInstance);
		}
		return currentTask;
	}

	/**
	 * 设置宽度、高度属性
	 * @param activity
	 * @param activityInfo
	 */
	private void setWidthAndHeight(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("width", activity.getWidth());
		activityInfo.put("height", activity.getHeight());
	}

	/**
	 * 设置坐标位置
	 * @param activity
	 * @param activityInfo
	 */
	private void setPosition(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("x", activity.getX());
		activityInfo.put("y", activity.getY());
	}


	/**
	 * 保存审核意见
	 * @param act
	 */
	@Transactional(readOnly = false)
	public void auditSave(Act act) {
		// 设置意见
		act.setComment(("yes".equals(act.getFlag())?"[同意] ":"[驳回] ")+act.getComment());
		act.preUpdate();
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = act.getTaskDefKey();
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(act.getFlag())? true : false);

		complete(act.getTaskId(), act.getProcInsId(), act.getComment(), vars);

	}


	/**
	 * 判断下一个节点是互斥网关还是用户任务节点
	 * @param  processInstanceId     任务Id信息
	 * @return  下一个用户任务用户组信息
	 * @throws Exception
	 */
	public boolean isNextGatewaty(String processInstanceId) {

		ProcessDefinitionEntity processDefinitionEntity = null;

		String id = null;

		ActivityImpl nextActivityImpl = null;

		//获取流程实例Id信息
		//String processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();


		//获取流程发布Id信息
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if(processInstance == null){
			return false;
		}
		String definitionId = processInstance.getProcessDefinitionId();

		processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(definitionId);

		ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

		//当前流程节点Id信息
		String activitiId = execution.getActivityId();

		//获取流程所有节点信息
		List<ActivityImpl> activitiList = processDefinitionEntity.getActivities();

		//遍历所有节点信息
		for(ActivityImpl activityImpl : activitiList){
			id = activityImpl.getId();
			if (activitiId.equals(id)) {
				//获取下一个节点信息
				{

					PvmActivity ac = null;

					Object s = null;

					// 如果遍历节点为用户任务并且节点不是当前节点信息
					if ("userTask".equals(activityImpl.getProperty("type")) && !activitiId.equals(activityImpl.getId())) {
						// 获取该节点下一个节点信息
						TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activityImpl.getActivityBehavior())
								.getTaskDefinition();
						return false;
					} else if("exclusiveGateway".equals(activityImpl.getProperty("type"))){// 当前节点为exclusiveGateway
						List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
						//outTransitionsTemp = ac.getOutgoingTransitions();

						// 如果网关路线判断条件为空信息
//          if (StringUtils.isEmpty(elString)) {
						// 获取流程启动时设置的网关判断条件信息
						String elString = getGatewayCondition(activityImpl.getId(), processInstanceId);
//          }
						// 如果排他网关只有一条线路信息
						if (outTransitions.size() == 1) {
							return false;
						} else if (outTransitions.size() > 1) { // 如果排他网关有多条线路信息
							return true;
						}
					}else {
						// 获取节点所有流向线路信息
						List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
						List<PvmTransition> outTransitionsTemp = null;
						for (PvmTransition tr : outTransitions) {
							ac = tr.getDestination(); // 获取线路的终点节点
							// 如果流向线路为排他网关
							if ("exclusiveGateway".equals(ac.getProperty("type"))) {
								return true;
							} else if ("userTask".equals(ac.getProperty("type"))) {
								return false;
							} else {
								return false;
							}
						}
						return false;
					}
					return false;
				}
			}
		}

		return false;
	}


	/**
	 * 下一个任务节点信息,
	 *
	 * 如果下一个节点为用户任务则直接返回,
	 *
	 * 如果下一个节点为排他网关, 获取排他网关Id信息, 根据排他网关Id信息和execution获取流程实例排他网关Id为key的变量值,
	 * 根据变量值分别执行排他网关后线路中的el表达式, 并找到el表达式通过的线路后的用户任务
	 * @param  activityImpl     流程节点信息
	 * @param  activityId             当前流程节点Id信息
	 * @param  elString               排他网关顺序流线段判断条件
	 * @param  processInstanceId      流程实例Id信息
	 * @return
	 */
	private TaskDefinition nextTaskDefinition(ActivityImpl activityImpl, String activityId, String elString, String processInstanceId){

		PvmActivity ac = null;

		Object s = null;

		// 如果遍历节点为用户任务并且节点不是当前节点信息
		if ("userTask".equals(activityImpl.getProperty("type")) && !activityId.equals(activityImpl.getId())) {
			// 获取该节点下一个节点信息
			TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activityImpl.getActivityBehavior())
					.getTaskDefinition();
			return taskDefinition;
		} else if("exclusiveGateway".equals(activityImpl.getProperty("type"))){// 当前节点为exclusiveGateway
			List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
			//outTransitionsTemp = ac.getOutgoingTransitions();

			// 如果网关路线判断条件为空信息
//          if (StringUtils.isEmpty(elString)) {
			// 获取流程启动时设置的网关判断条件信息
			elString = getGatewayCondition(activityImpl.getId(), processInstanceId);
//          }
			// 如果排他网关只有一条线路信息
			if (outTransitions.size() == 1) {
				return nextTaskDefinition((ActivityImpl) outTransitions.get(0).getDestination(), activityId,
						elString, processInstanceId);
			} else if (outTransitions.size() > 1) { // 如果排他网关有多条线路信息
				for (PvmTransition tr1 : outTransitions) {
					s = tr1.getProperty("conditionText"); // 获取排他网关线路判断条件信息
					// 判断el表达式是否成立
					if (isCondition(activityImpl.getId(), StringUtils.trim(s.toString()), elString)) {
						return nextTaskDefinition((ActivityImpl) tr1.getDestination(), activityId, elString,
								processInstanceId);
					}

				}
			}
		}else {
			// 获取节点所有流向线路信息
			List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
			List<PvmTransition> outTransitionsTemp = null;
			for (PvmTransition tr : outTransitions) {
				ac = tr.getDestination(); // 获取线路的终点节点
				// 如果流向线路为排他网关
				if ("exclusiveGateway".equals(ac.getProperty("type"))) {
					outTransitionsTemp = ac.getOutgoingTransitions();

					// 如果网关路线判断条件为空信息
					if (StringUtils.isEmpty(elString)) {
						// 获取流程启动时设置的网关判断条件信息
						elString = getGatewayCondition(ac.getId(), processInstanceId);
					}

					// 如果排他网关只有一条线路信息
					if (outTransitionsTemp.size() == 1) {
						return nextTaskDefinition((ActivityImpl) outTransitionsTemp.get(0).getDestination(), activityId,
								elString, processInstanceId);
					} else if (outTransitionsTemp.size() > 1) { // 如果排他网关有多条线路信息
						for (PvmTransition tr1 : outTransitionsTemp) {
							s = tr1.getProperty("conditionText"); // 获取排他网关线路判断条件信息
							// 判断el表达式是否成立
							if (isCondition(ac.getId(), StringUtils.trim(s.toString()), elString)) {
								return nextTaskDefinition((ActivityImpl) tr1.getDestination(), activityId, elString,
										processInstanceId);
							}
						}
					}
				} else if ("userTask".equals(ac.getProperty("type"))) {
					return ((UserTaskActivityBehavior) ((ActivityImpl) ac).getActivityBehavior()).getTaskDefinition();
				} else {
				}
			}
			return null;
		}
		return null;
	}

	/**
	 * 查询流程启动时设置排他网关判断条件信息
	 * @param  gatewayId          排他网关Id信息, 流程启动时设置网关路线判断条件key为网关Id信息
	 * @param  processInstanceId  流程实例Id信息
	 * @return
	 */
	public String getGatewayCondition(String gatewayId, String processInstanceId) {
		Execution execution = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).singleResult();
		Object object= runtimeService.getVariable(execution.getId(), gatewayId);
		return object==null? "":object.toString();
	}

	/**
	 * 根据key和value判断el表达式是否通过信息
	 * @param  key    el表达式key信息
	 * @param  el     el表达式信息
	 * @param  value  el表达式传入值信息
	 * @return
	 */
	public boolean isCondition(String key, String el, String value) {
		ExpressionFactory factory = new ExpressionFactoryImpl();
		SimpleContext context = new SimpleContext();
		context.setVariable(key, factory.createValueExpression(value, String.class));
		ValueExpression e = factory.createValueExpression(context, el, boolean.class);
		return (Boolean) e.getValue(context);
	}


}
