/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.iim.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 聊天记录Entity
 * @author jeeplus
 * @version 2015-12-29
 */
public class ChatHistory extends DataEntity<ChatHistory> {
	
	private static final long serialVersionUID = 1L;
	private String sender;		// sender 发送者登录名loginName
	private String receiver;		// receiver 接收者登录名loginName
	private String msg;		// msg
	private String status;		// status
	private String type;//聊天类型
	
	public ChatHistory() {
		super();
	}

	public ChatHistory(String id){
		super(id);
	}

	@Length(min=0, max=64, message="userid1长度必须介于 0 和 64 之间")
	@ExcelField(title="sender", align=2, sort=1)
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	@Length(min=0, max=64, message="userid2长度必须介于 0 和 64 之间")
	@ExcelField(title="receiver", align=2, sort=2)
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	@Length(min=0, max=1024, message="msg长度必须介于 0 和 1024 之间")
	@ExcelField(title="msg", align=2, sort=3)
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Length(min=0, max=45, message="status长度必须介于 0 和 45 之间")
	@ExcelField(title="status", align=2, sort=4)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}