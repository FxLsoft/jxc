/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.act.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import com.jeeplus.common.json.AjaxJson;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.act.service.ActProcessService;

/**
 * 流程定义相关Controller
 * @author jeeplus
 * @version 2016-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/act/process")
public class ActProcessController extends BaseController {

	@Autowired
	private ActProcessService actProcessService;

	 @Autowired
	 private HistoryService historyService;
	/**
	 * 流程定义列表
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = {"list", ""})
	public String processList(String category, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*
		 * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		 */
		return "modules/bpm/process/definition/processList";
	}



	@ResponseBody
	@RequiresPermissions("act:process:edit")
	@RequestMapping("list/data")
	public Map<String, Object> processListData(String category, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*
		 * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		 */
		Page<Map> page = actProcessService.processList(new Page<Map>(request, response), category);
		return getBootstrapData(page);
	}
	
	/**
	 * 运行中的实例列表
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "running")
	public String runningList(String procInsId, String procDefKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/bpm/process/running/processRunningList";
	}

	@ResponseBody
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "runningData")
	public Map<String, Object> runningListData(String procInsId, String procDefKey, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Page<HashMap<String,Object>> page = actProcessService.runningList(new Page<ProcessInstance>(request, response), procInsId, procDefKey);
		return getBootstrapData(page);
	}

	/**
	 * 已结束的实例
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "historyList")
	public String historyList(String procInsId, String procDefKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/bpm/process/history/processHistoryList";
	}

	/**
	 * 已结束的实例
	 */
	@ResponseBody
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "historyListData")
	public  Map<String, Object> historyListData(String procInsId, String procDefKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HistoricProcessInstance> page = actProcessService.historyList(new Page<HistoricProcessInstance>(request, response), procInsId, procDefKey);
		return getBootstrapData(page);
	}
	
	
	/**
	 * 读取资源，通过部署ID
	 * @param response
	 * @throws Exception
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "resource/read")
	public void resourceRead(String procDefId, String proInsId, String resType, HttpServletResponse response) throws Exception {
		InputStream resourceAsStream = actProcessService.resourceRead(procDefId, proInsId, resType);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	/**
	 * 部署流程
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "/deploy", method=RequestMethod.GET)
	public String deploy(Model model) {
		return "modules/bpm/process/deploy/processDeploy";
	}
	
	/**
	 * 部署流程 - 保存
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "/deploy", method=RequestMethod.POST)
	public AjaxJson deploy(@Value("#{APP_PROP['activiti.export.diagram.path']}") String exportDir,
			String category, MultipartFile file) {

		AjaxJson j = new AjaxJson();
		String fileName = file.getOriginalFilename();
		
		if (StringUtils.isBlank(fileName)){
			j.setSuccess(false);
			j.setMsg("请选择要部署的流程文件");
		}else{
			j = actProcessService.deploy(exportDir, category, file);
		}
		return j;
	}
	
	/**
	 * 设置流程分类
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "updateCategory")
	public String updateCategory(String procDefId, String category, RedirectAttributes redirectAttributes) {
		actProcessService.updateCategory(procDefId, category);
		return "redirect:" + adminPath + "/act/process";
	}

	/**
	 * 挂起、激活流程实例
	 */
	@ResponseBody
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "update/{state}")
	public AjaxJson updateState(@PathVariable("state") String state, String procDefId) {
		AjaxJson j = new AjaxJson();
		String message = actProcessService.updateState(state, procDefId);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 将部署的流程转换为模型
	 * @param procDefId
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws XMLStreamException
	 */
	@ResponseBody
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "convert/toModel")
	public AjaxJson convertToModel(String procDefId) throws UnsupportedEncodingException, XMLStreamException {
		AjaxJson j = new AjaxJson();
		org.activiti.engine.repository.Model modelData = actProcessService.convertToModel(procDefId);
		j.setMsg("转换模型成功，模型ID=" + modelData.getId());
		return j;
	}
	
	/**
	 * 导出图片文件到硬盘
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "export/diagrams")
	@ResponseBody
	public List<String> exportDiagrams(@Value("#{APP_PROP['activiti.export.diagram.path']}") String exportDir) throws IOException {
		List<String> files = actProcessService.exportDiagrams(exportDir);;
		return files;
	}

	/**
	 * 删除部署的流程，级联删除流程实例
	 * @param deploymentId 流程部署ID
	 */
	@ResponseBody
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String deploymentId) {
		AjaxJson j = new AjaxJson();
		actProcessService.deleteDeployment(deploymentId);
		j.setMsg("删除成功!");
		return j;
	}
	
	/**
	 * 删除流程实例
	 * @param procInsId 流程实例ID
	 * @param reason 删除原因
	 */
	@ResponseBody
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "deleteProcIns")
	public AjaxJson deleteProcIns(String procInsId, String reason) {
		AjaxJson j = new AjaxJson();
		if (StringUtils.isBlank(reason)){
			j.setSuccess(false);
			j.setMsg("请填写作废原因");
		}else{
			try{
				actProcessService.deleteProcIns(procInsId, reason);
				j.setSuccess(true);
				j.setMsg("作废成功，流程实例ID=" + procInsId);
			}catch (ActivitiObjectNotFoundException e){
				j.setSuccess(false);
				j.setMsg("操作失败，流程已经结束!");
			}

		}
		return j;
	}
	
}
