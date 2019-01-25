/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.iim.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;

/**
 * 日历Entity
 * @author liugf
 * @version 2016-04-19
 */
public class MyCalendar extends DataEntity<MyCalendar> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 事件标题
	private Date start;		// 事件开始时间
	private Date end;		// 事件结束时间
	private String adllDay;		// 是否为全天时间
	private String color;		// 时间的背景色
	private User user;		// 所属用户
	
	public MyCalendar() {
		super();
	}

	public MyCalendar(String id){
		super(id);
	}

	@Length(min=0, max=64, message="事件标题长度必须介于 0 和 64 之间")
	@ExcelField(title="事件标题", align=2, sort=1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8") 
	@Length(min=0, max=64, message="事件开始时间长度必须介于 0 和 64 之间")
	@ExcelField(title="事件开始时间", align=2, sort=2)
	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8") 
	@Length(min=0, max=64, message="事件结束时间长度必须介于 0 和 64 之间")
	@ExcelField(title="事件结束时间", align=2, sort=3)
	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	@JsonIgnore
	@Length(min=0, max=64, message="是否为全天时间长度必须介于 0 和 64 之间")
	@ExcelField(title="是否为全天时间", align=2, sort=4)
	public String getAdllDay() {
		return adllDay;
	}

	public void setAdllDay(String adllDay) {
		this.adllDay = adllDay;
	}
	
	@Length(min=0, max=64, message="时间的背景色长度必须介于 0 和 64 之间")
	@ExcelField(title="时间的背景色", align=2, sort=5)
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
}