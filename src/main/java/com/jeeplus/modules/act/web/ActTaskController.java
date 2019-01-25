/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.act.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeeplus.common.json.AjaxJson;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.act.entity.Act;
import com.jeeplus.modules.act.service.ActTaskService;
import com.jeeplus.modules.act.utils.ActUtils;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 流程个人任务相关Controller
 * @author jeeplus
 * @version 2016-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/act/task")
public class ActTaskController extends BaseController {

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private TaskService taskService;
	/**
	 * 获取待办列表
	 * @return
	 */
	@RequestMapping(value = {"todo", ""})
	public String todoList(Act act, HttpServletResponse response, Model model) throws Exception {
		return "modules/bpm/task/todo/taskTodoList";
	}

	@ResponseBody
	@RequestMapping(value = "todo/data")
	public  Map<String, Object> todoListData(Act act, HttpServletRequest request,HttpServletResponse response, Model model) throws Exception {
		Page<HashMap<String,String>> page = actTaskService.todoList(new  Page<HashMap<String,String>>(request, response),act);
		Map<String, Object> map = new HashMap<String, Object>();
		return getBootstrapData(page);
	}
	
	/**
	 * 获取已办任务
	 * @return
	 */
	@RequestMapping(value = "historic")
	public String historicList(Act act, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "modules/bpm/task/history/taskHistoricList";
	}

	@ResponseBody
	@RequestMapping(value = "historic/data")
	public Map<String, Object>  historicListData(Act act, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Page<HashMap<String,String>> page = actTaskService.historicList(new  Page<HashMap<String,String>>(request, response), act);
		return getBootstrapData(page);
	}

	/**
	 * 获取我的申请列表
	 * @return
	 */
	@RequestMapping(value = "myApplyed")
	public String myApplyedList(Act act, HttpServletResponse response, Model model) throws Exception {
		return "modules/bpm/task/apply/taskMyAppledList";
	}

	@ResponseBody
	@RequestMapping(value = "myApplyed/data")
	public  Map<String, Object> myApplyedListData(Act act, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Page<HashMap> page = actTaskService.getMyStartedProcIns(UserUtils.getUser(),new Page<HashMap>(request, response));
		return getBootstrapData(page);
	}

	/**
	 * 获取流转历史列表
	 * @param startAct 开始活动节点名称
	 * @param endAct 结束活动节点名称
	 */
	@RequestMapping(value = "histoicFlow")
	public String histoicFlow(Act act, String startAct, String endAct, Model model){
		if (StringUtils.isNotBlank(act.getProcInsId())){
			List<Act> histoicFlowList = actTaskService.histoicFlowList(act.getProcInsId(), startAct, endAct);
			model.addAttribute("histoicFlowList", histoicFlowList);
		}
		return "modules/bpm/task/history/taskHistoricFlow";
	}
	
	/**
	 * 获取流程流向图
	 * @param startAct 开始活动节点名称
	 * @param endAct 结束活动节点名称
	 */
	@RequestMapping(value = "flowChart")
	public String flowChart(Act act, String startAct, String endAct, Model model){
		if (StringUtils.isNotBlank(act.getProcInsId())){
			List<Act> histoicFlowList = actTaskService.histoicFlowList(act.getProcInsId(), startAct, endAct);
			model.addAttribute("histoicFlowList", histoicFlowList);
		}
		return "modules/bpm/task/chart/taskFlowChart";
	}
	
	/**
	 * 获取流程列表
	 * @param category 流程分类
	 */
	@RequestMapping(value = "process")
	public String processList(String category, HttpServletRequest request, HttpServletResponse response, Model model) {
	    Page<Object[]> page = new Page<Object[]>(request, response);
		model.addAttribute("category", category);
		return "modules/bpm/task/process/taskProcessList";
	}

	/**
	 * 获取流程表单
	 */
	@RequestMapping(value = "form")
	public String form(Act act, HttpServletRequest request, Model model){
		// 获取流程XML上的表单KEY
		String formKey = actTaskService.getFormKey(act.getProcDefId(), act.getTaskDefKey());
		// 获取流程实例对象
		if (act.getProcInsId() != null){
			if(actTaskService.getProcIns(act.getProcInsId())!=null){
				act.setProcIns(actTaskService.getProcIns(act.getProcInsId()));
			}else{
				act.setFinishedProcIns(actTaskService.getFinishedProcIns(act.getProcInsId()));
			}

			if(actTaskService.isNextGatewaty(act.getProcInsId())){
				act.setIsNextGatewaty(true);
			}else{
				act.setIsNextGatewaty(false);
			}
		
		}
		return "redirect:" + ActUtils.getFormUrl(formKey, act);
	}


	/**
	 * 根据流程实例ID,获取当前节点的流程表单详情
	 */
	@RequestMapping(value = "formDetail")
	public String formDetail(Act act, HttpServletRequest request, Model model){
		// 获取流程XML上的表单KEY
		String taskDefKey = nextTaskDefinition(act.getProcInsId());
		String formKey = actTaskService.getFormKey(act.getProcDefId(), taskDefKey);
		// 获取流程实例对象
		if (act.getProcInsId() != null){
			if(actTaskService.getProcIns(act.getProcInsId())!=null){
				act.setProcIns(actTaskService.getProcIns(act.getProcInsId()));
			}else{
				act.setFinishedProcIns(actTaskService.getFinishedProcIns(act.getProcInsId()));
			}

			if(actTaskService.isNextGatewaty(act.getProcInsId())){
				act.setIsNextGatewaty(true);
			}else{
				act.setIsNextGatewaty(false);
			}

		}
		return "redirect:" + ActUtils.getFormUrl(formKey, act);
	}
	/**
	 * 启动流程
	 */
	@RequestMapping(value = "start")
	@ResponseBody
	public String start(Act act, String table, String id, Model model) throws Exception {
		actTaskService.startProcess(act.getProcDefKey(), act.getBusinessId(), act.getBusinessTable(), act.getTitle());
		return "true";//adminPath + "/act/task";
	}

	/**
	 * 签收任务
	 */
	@RequestMapping(value = "claim")
	@ResponseBody
	public String claim(Act act) {
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
		actTaskService.claim(act.getTaskId(), userId);
		return "true";//adminPath + "/act/task";
	}
	
	/**
	 * 完成任务
	 * 		vars.keys=flag,pass
	 * 		vars.values=1,true
	 * 		vars.types=S,B  @see com.jeeplus.jeeplus.modules.act.utils.PropertyType
	 */
	@RequestMapping(value = "complete")
	@ResponseBody
	public String complete(Act act) {
		actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(), act.getVars().getVariableMap());
		return "true";//adminPath + "/act/task";
	}
	
	/**
	 * 读取带跟踪的图片
	 */
	@RequestMapping(value = "trace/photo/{procDefId}/{execId}")
	public void tracePhoto(@PathVariable("procDefId") String procDefId, @PathVariable("execId") String execId, HttpServletResponse response) throws Exception {
		InputStream imageStream = actTaskService.tracePhoto(procDefId, execId);
		
		// 输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}
	
	/**
	 * 输出跟踪流程信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "trace/info/{proInsId}")
	public List<Map<String, Object>> traceInfo(@PathVariable("proInsId") String proInsId) throws Exception {
		List<Map<String, Object>> activityInfos = actTaskService.traceProcess(proInsId);
		return activityInfos;
	}

	/**
	 * 显示流程图
	 
	@RequestMapping(value = "processPic")
	public void processPic(String procDefId, HttpServletResponse response) throws Exception {
		ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
		String diagramResourceName = procDef.getDiagramResourceName();
		InputStream imageStream = repositoryService.getResourceAsStream(procDef.getDeploymentId(), diagramResourceName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}*/
	
	/**
	 * 获取跟踪信息
	 
	@RequestMapping(value = "processMap")
	public String processMap(String procDefId, String proInstId, Model model)
			throws Exception {
		List<ActivityImpl> actImpls = new ArrayList<ActivityImpl>();
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery().processDefinitionId(procDefId)
				.singleResult();
		ProcessDefinitionImpl pdImpl = (ProcessDefinitionImpl) processDefinition;
		String processDefinitionId = pdImpl.getId();// 流程标识
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		List<ActivityImpl> activitiList = def.getActivities();// 获得当前任务的所有节点
		List<String> activeActivityIds = runtimeService.getActiveActivityIds(proInstId);
		for (String activeId : activeActivityIds) {
			for (ActivityImpl activityImpl : activitiList) {
				String id = activityImpl.getId();
				if (activityImpl.isScope()) {
					if (activityImpl.getActivities().size() > 1) {
						List<ActivityImpl> subAcList = activityImpl
								.getActivities();
						for (ActivityImpl subActImpl : subAcList) {
							String subid = subActImpl.getId();
							System.out.println("subImpl:" + subid);
							if (activeId.equals(subid)) {// 获得执行到那个节点
								actImpls.add(subActImpl);
								break;
							}
						}
					}
				}
				if (activeId.equals(id)) {// 获得执行到那个节点
					actImpls.add(activityImpl);
					System.out.println(id);
				}
			}
		}
		model.addAttribute("procDefId", procDefId);
		model.addAttribute("proInstId", proInstId);
		model.addAttribute("actImpls", actImpls);
		return "modules/act/actTaskMap";
	}*/
	
	/**
	 * 删除任务
	 * @param taskId 流程实例ID
	 * @param reason 删除原因
	 */
	@ResponseBody
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "deleteTask")
	public AjaxJson deleteTask(String taskId, String reason) {
		AjaxJson j = new AjaxJson();
		if (StringUtils.isBlank(reason)){
			j.setSuccess(false);
			j.setMsg("请填写删除原因");
		}else{
			actTaskService.deleteTask(taskId, reason);
			j.setSuccess(true);
			j.setMsg("删除任务成功，任务ID=" + taskId);
		}
		return j;
	}

	/**
	 * 审批
	 * @param act
	 */
	@ResponseBody
	@RequestMapping(value = "audit")
	public AjaxJson auditTask(Act act) {
		AjaxJson j = new AjaxJson();
		actTaskService.auditSave(act);
		j.setMsg("审批成功");
		return j;
	}

	/**
	 * 取回流程
	 * @param taskId
	 */
	@ResponseBody
	@RequestMapping(value = "callback")
	public AjaxJson callback(@Param("taskId") String taskId) {
		AjaxJson j = new AjaxJson();
		try {
			Map<String, Object> variables;
			// 取得当前任务
			HistoricTaskInstance currTask = historyService
					.createHistoricTaskInstanceQuery().taskId(taskId)
					.singleResult();
			// 取得流程实例
			ProcessInstance instance = runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(currTask.getProcessInstanceId())
					.singleResult();
			if (instance == null) {
				j.setSuccess(false);
				j.setMsg("流程已经结束");
				return j;
			}
			variables=instance.getProcessVariables();
			// 取得流程定义
			ProcessDefinitionEntity definition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(currTask
							.getProcessDefinitionId());
			if (definition == null) {
				j.setSuccess(false);
				j.setMsg("流程定义未找到");
				return j;
			}
			// 取得下一步活动
			ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
					.findActivity(currTask.getTaskDefinitionKey());
			List<PvmTransition> nextTransitionList = currActivity
					.getOutgoingTransitions();
			for (PvmTransition nextTransition : nextTransitionList) {
				PvmActivity nextActivity = nextTransition.getDestination();
				List<HistoricTaskInstance> completeTasks = historyService
						.createHistoricTaskInstanceQuery()
						.processInstanceId(instance.getId())
						.taskDefinitionKey(nextActivity.getId()).finished()
						.list();
				int finished = completeTasks.size();
				if (finished > 0) {
					j.setSuccess(false);
					j.setMsg("存在已经完成的下一步，流程不能取回");
					return j;
				}
				List<Task> nextTasks = taskService.createTaskQuery().processInstanceId(instance.getId())
						.taskDefinitionKey(nextActivity.getId()).list();
				for (Task nextTask : nextTasks) {
					//取活动，清除活动方向
					List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
					List<PvmTransition> pvmTransitionList = nextActivity
							.getOutgoingTransitions();
					for (PvmTransition pvmTransition : pvmTransitionList) {
						oriPvmTransitionList.add(pvmTransition);
					}
					pvmTransitionList.clear();
					//建立新方向
					ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition)
							.findActivity(nextTask.getTaskDefinitionKey());
					TransitionImpl newTransition = nextActivityImpl
							.createOutgoingTransition();
					newTransition.setDestination(currActivity);
					//完成任务
					taskService.complete(nextTask.getId(), variables);
					historyService.deleteHistoricTaskInstance(nextTask.getId());
					//恢复方向
					currActivity.getIncomingTransitions().remove(newTransition);
					List<PvmTransition> pvmTList = nextActivity
							.getOutgoingTransitions();
					pvmTList.clear();
					for (PvmTransition pvmTransition : oriPvmTransitionList) {
						pvmTransitionList.add(pvmTransition);
					}
				}
			}
			historyService.deleteHistoricTaskInstance(currTask.getId());
			j.setSuccess(true);
			j.setMsg("取回成功");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("流程取回失败，未知错误.");
			return j;
		}
	}


	/**
	 * 根据实例编号查找下一个任务节点
	 * @param  procInstId ：实例编号
	 * @return
	 */
	public String nextTaskDefinition(String procInstId){
		//流程标示
		String processDefinitionId = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInstId).singleResult().getProcessDefinitionId();

		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinitionId);
		//执行实例
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery().processInstanceId(procInstId).singleResult();
		//当前实例的执行到哪个节点
		if(execution == null){
			TaskDefinition[] taskDefinitions = {};
			taskDefinitions = def.getTaskDefinitions().values().toArray(taskDefinitions);
			return taskDefinitions[taskDefinitions.length -1].getKey();
		}else{
			return  execution.getActivityId();
		}
	}

}
