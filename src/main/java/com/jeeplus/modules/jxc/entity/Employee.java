/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 员工信息Entity
 * @author FxLsoft
 * @version 2018-12-22
 */
public class Employee extends DataEntity<Employee> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String phone;		// 电话
	private Date birthday;		// 出生日期
	private String salary;		// 薪水
	private String bankAccount;		// 银行账号
	private String bankName;		// 开户行
	
	public Employee() {
		super();
	}

	public Employee(String id){
		super(id);
	}

	@ExcelField(title="姓名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="电话", align=2, sort=2)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="出生日期不能为空")
	@ExcelField(title="出生日期", align=2, sort=3)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@ExcelField(title="薪水", align=2, sort=4)
	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}
	
	@ExcelField(title="银行账号", align=2, sort=5)
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	@ExcelField(title="开户行", align=2, sort=6)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
}