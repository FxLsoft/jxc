/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.act.web;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.act.service.ActProcessService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程定义相关Controller
 * @author jeeplus
 * @version 2016-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/act/process/history")
public class ActProcessHistoryController extends BaseController {

	@Autowired
	private ActProcessService actProcessService;

	 @Autowired
	 private HistoryService historyService;

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
	 * 删除历史流程实例
	 * @param procInsId 流程实例ID
	 */
	@ResponseBody
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "deleteProcIns")
	public AjaxJson deleteProcIns(String procInsId) {
		AjaxJson j = new AjaxJson();
		actProcessService.delHistoryProcInsById(procInsId);
		j.setSuccess(true);
		j.setMsg("删除成功，流程实例ID=" + procInsId);
		return j;
	}

	/**
	 * 删除历史流程实例
	 * @param procInsIds 流程实例ID
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAllProcIns")
	public AjaxJson deleteAllProcIns(String procInsIds) {
		AjaxJson j = new AjaxJson();
		String[] procInsIdArra = procInsIds.split(",");
		for(String procInsId: procInsIdArra){
			actProcessService.delHistoryProcInsById(procInsId);
		}
		j.setSuccess(true);
		j.setMsg("删除成功，流程实例ID=" + procInsIds);
		return j;
	}
	
}
