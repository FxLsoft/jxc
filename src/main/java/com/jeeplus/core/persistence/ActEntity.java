/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.core.persistence;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeeplus.modules.act.entity.Act;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;

/**
 * Activiti Entity类
 * @author jeeplus
 * @version 2016-05-28
 */
public abstract class ActEntity<T> extends DataEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Act act; 		// 流程任务对象

	public ActEntity() {
		super();
	}
	
	public ActEntity(String id) {
		super(id);
	}

	private User user;	//	归属用户

	private Office office;	//	归属部门

	private String 	content;	//	调整原因

	private String businessTable;


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@JsonIgnore
	public Act getAct() {
		if (act == null){
			act = new Act();
		}
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	/**
	 * 获取流程实例ID
	 * @return
	 */
	public String getProcInsId() {
		return this.getAct().getProcInsId();
	}

	/**
	 * 设置流程实例ID
	 * @param procInsId
	 */
	public void setProcInsId(String procInsId) {
		this.getAct().setProcInsId(procInsId);
	}

	public String getBusinessTable() {
		return businessTable;
	}

	public void setBusinessTable(String businessTable) {
		this.businessTable = businessTable;
	}
}
