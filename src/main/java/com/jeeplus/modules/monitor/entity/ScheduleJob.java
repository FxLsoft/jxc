/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.monitor.entity;


import javax.validation.constraints.NotNull;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 定时任务Entity
 * @author lgf
 * @version 2017-02-04
 */
public class ScheduleJob extends DataEntity<ScheduleJob> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 任务名
	private String group;		// 任务组
	private String cronExpression;		// 定时规则
	private String status;		// 启用状态
	private String isInfo;		// 通知用户
	private String className;		// 任务类
	private String description;		// 描述
	
	public ScheduleJob() {
		super();
	}

	public ScheduleJob(String id){
		super(id);
	}

	@NotNull(message="任务名不能为空")
	@ExcelField(title="任务名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="任务组不能为空")
	@ExcelField(title="任务组", dictType="schedule_task_group", align=2, sort=2)
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	@NotNull(message="定时规则不能为空")
	@ExcelField(title="定时规则", align=2, sort=3)
	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	@NotNull(message="启用状态不能为空")
	@ExcelField(title="启用状态", dictType="yes_no", align=2, sort=4)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="通知用户", dictType="schedule_task_info", align=2, sort=5)
	public String getIsInfo() {
		return isInfo;
	}

	public void setIsInfo(String isInfo) {
		this.isInfo = isInfo;
	}
	
	@NotNull(message="任务类不能为空")	
	@ExcelField(title="任务类", align=2, sort=6)
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	@ExcelField(title="描述", align=2, sort=7)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}