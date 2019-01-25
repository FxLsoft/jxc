/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.activiti.entity;

import com.jeeplus.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.ActEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 报销申请Entity
 * @author 刘高峰
 * @version 2018-06-16
 */
public class TestExpense extends ActEntity<TestExpense> {
	
	private static final long serialVersionUID = 1L;
	private User tuser;		// 员工姓名
	private Office office;		// 所属部门
	private String cost;		// 报销费用
	private String reason;		// 报销事由
	private String procInsId;		// 流程实例id
	
	public TestExpense() {
		super();
	}

	public TestExpense(String id){
		super(id);
	}

	@NotNull(message="员工姓名不能为空")
	@ExcelField(title="员工姓名", fieldType=User.class, value="tuser.name", align=2, sort=1)
	public User getTuser() {
		return tuser;
	}

	public void setTuser(User tuser) {
		this.tuser = tuser;
	}
	
	@NotNull(message="所属部门不能为空")
	@ExcelField(title="所属部门", fieldType=Office.class, value="office.name", align=2, sort=2)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@ExcelField(title="报销费用", align=2, sort=3)
	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}
	
	@ExcelField(title="报销事由", align=2, sort=4)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@ExcelField(title="流程实例id", align=2, sort=11)
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
}