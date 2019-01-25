/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.activiti.entity;

import com.jeeplus.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.sys.entity.Office;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.ActEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 薪酬调整申请Entity
 * @author 刘高峰
 * @version 2018-06-16
 */
public class TestAudit extends ActEntity<TestAudit> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例ID
	private User user;		// 变动用户
	private Office office;		// 归属部门
	private String post;		// 岗位
	private String sex;		// 性别
	private String edu;		// 学历
	private String content;		// 调整原因
	private String olda;		// 现行标准 薪酬档级
	private String oldb;		// 现行标准 月工资额
	private String oldc;		// 现行标准 年薪总额
	private String newa;		// 调整后标准 薪酬档级
	private String newb;		// 调整后标准 月工资额
	private String newc;		// 调整后标准 年薪总额
	private String addNum;		// 月增资
	private Date exeDate;		// 执行时间
	
	public TestAudit() {
		super();
	}

	public TestAudit(String id){
		super(id);
	}

	@ExcelField(title="流程实例ID", align=2, sort=1)
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@NotNull(message="变动用户不能为空")
	@ExcelField(title="变动用户", fieldType=User.class, value="user.name", align=2, sort=2)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@NotNull(message="归属部门不能为空")
	@ExcelField(title="归属部门", fieldType=Office.class, value="office.name", align=2, sort=3)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@ExcelField(title="岗位", align=2, sort=4)
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	@ExcelField(title="性别", dictType="sex", align=2, sort=5)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@ExcelField(title="学历", align=2, sort=6)
	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}
	
	@ExcelField(title="调整原因", align=2, sort=7)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="现行标准 薪酬档级", align=2, sort=8)
	public String getOlda() {
		return olda;
	}

	public void setOlda(String olda) {
		this.olda = olda;
	}
	
	@ExcelField(title="现行标准 月工资额", align=2, sort=9)
	public String getOldb() {
		return oldb;
	}

	public void setOldb(String oldb) {
		this.oldb = oldb;
	}
	
	@ExcelField(title="现行标准 年薪总额", align=2, sort=10)
	public String getOldc() {
		return oldc;
	}

	public void setOldc(String oldc) {
		this.oldc = oldc;
	}
	
	@ExcelField(title="调整后标准 薪酬档级", align=2, sort=11)
	public String getNewa() {
		return newa;
	}

	public void setNewa(String newa) {
		this.newa = newa;
	}
	
	@ExcelField(title="调整后标准 月工资额", align=2, sort=12)
	public String getNewb() {
		return newb;
	}

	public void setNewb(String newb) {
		this.newb = newb;
	}
	
	@ExcelField(title="调整后标准 年薪总额", align=2, sort=13)
	public String getNewc() {
		return newc;
	}

	public void setNewc(String newc) {
		this.newc = newc;
	}
	
	@ExcelField(title="月增资", align=2, sort=14)
	public String getAddNum() {
		return addNum;
	}

	public void setAddNum(String addNum) {
		this.addNum = addNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="执行时间", align=2, sort=15)
	public Date getExeDate() {
		return exeDate;
	}

	public void setExeDate(Date exeDate) {
		this.exeDate = exeDate;
	}
	
}