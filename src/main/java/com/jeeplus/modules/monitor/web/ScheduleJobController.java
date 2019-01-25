/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.monitor.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronExpression;
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
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.monitor.entity.ScheduleJob;
import com.jeeplus.modules.monitor.service.ScheduleJobService;

/**
 * 定时任务Controller
 * @author lgf
 * @version 2017-02-04
 */
@Controller
@RequestMapping(value = "${adminPath}/monitor/scheduleJob")
public class ScheduleJobController extends BaseController {

	@Autowired
	private ScheduleJobService scheduleJobService;
	
	@ModelAttribute
	public ScheduleJob get(@RequestParam(required=false) String id) {
		ScheduleJob entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scheduleJobService.get(id);
		}
		if (entity == null){
			entity = new ScheduleJob();
		}
		return entity;
	}
	
	/**
	 * 定时任务列表页面
	 */
	@RequiresPermissions("monitor:scheduleJob:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/monitor/task/scheduleJobList";
	}
	
		/**
	 * 定时任务列表数据
	 */
	@ResponseBody
	@RequiresPermissions("monitor:scheduleJob:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ScheduleJob scheduleJob, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ScheduleJob> page = scheduleJobService.findPage(new Page<ScheduleJob>(request, response), scheduleJob); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑定时任务表单页面
	 */
	@RequiresPermissions(value={"monitor:scheduleJob:view","monitor:scheduleJob:add","monitor:scheduleJob:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ScheduleJob scheduleJob, Model model) {
		model.addAttribute("scheduleJob", scheduleJob);
		return "modules/monitor/task/scheduleJobForm";
	}

	/**
	 * 保存定时任务
	 */
	@ResponseBody
	@RequiresPermissions(value={"monitor:scheduleJob:add","monitor:scheduleJob:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ScheduleJob scheduleJob, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(scheduleJob);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//验证cron表达式
		if(!CronExpression.isValidExpression(scheduleJob.getCronExpression())){
			j.setSuccess(false);
			j.setMsg("cron表达式不正确！");
			return j;
		}

		scheduleJobService.save(scheduleJob);//保存

		j.setMsg("保存定时任务成功!");
		return j;
	}
	
	/**
	 * 删除定时任务
	 */
	@ResponseBody
	@RequiresPermissions("monitor:scheduleJob:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ScheduleJob scheduleJob, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		scheduleJobService.delete(scheduleJob);
		j.setMsg("删除定时任务成功");
		return j;
	}
	
	/**
	 * 批量删除定时任务
	 */
	@ResponseBody
	@RequiresPermissions("monitor:scheduleJob:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			scheduleJobService.delete(scheduleJobService.get(id));
		}
		j.setMsg("删除定时任务成功");
		return j;
	}
	
	
	
	/**
	 * 暂停任务
	 */
	@RequiresPermissions("monitor:scheduleJob:stop")
	@RequestMapping(value="stop")
	@ResponseBody
	public AjaxJson stop(ScheduleJob scheduleJob) {
		AjaxJson j = new AjaxJson();
		scheduleJobService.stopJob(scheduleJob);
		j.setSuccess(true);
		j.setMsg("暂停成功!");
		return j;
	}


	/**
	 * 立即运行一次
	 */
	@RequiresPermissions("monitor:scheduleJob:startNow")
	@RequestMapping("startNow")
	@ResponseBody
	public AjaxJson stratNow(ScheduleJob scheduleJob) {
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
		j.setMsg("运行成功");
		scheduleJobService.startNowJob(scheduleJob);
		return j;
	}

	/**
	 * 恢复
	 */
	@RequiresPermissions("monitor:scheduleJob:resume")
	@RequestMapping("resume")
	@ResponseBody
	public AjaxJson resume(ScheduleJob scheduleJob, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
		j.setMsg("恢复成功");
		scheduleJobService.restartJob(scheduleJob);
		scheduleJobService.startNowJob(scheduleJob);//恢复之后，立即触发一次激活定时任务，不然定时任务有可能不会执行，这是bug的回避案，具体原因我没找到。
		return j;
	}
	
	/**
	 * 验证类任务类是否存在
	 */
	@RequestMapping("existsClass")
	@ResponseBody
	public boolean existsClass(String className) {
		Class job = null;
		try {
			job = Class.forName(className);
			return true;
		} catch (ClassNotFoundException e1) {
			return false;
		}
	}

}