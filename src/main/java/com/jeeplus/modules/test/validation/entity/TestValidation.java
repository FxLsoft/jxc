/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.validation.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.URL;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 测试校验功能Entity
 * @author lgf
 * @version 2018-06-12
 */
public class TestValidation extends DataEntity<TestValidation> {
	
	private static final long serialVersionUID = 1L;
	private Double num;		// 浮点数字
	private Integer num2;		// 整数
	private String str;		// 字符串
	private String email;		// 邮件
	private String url;		// 网址
	private Date newDate;		// 日期
	private String c1;		// 浮点数小于等于0
	private String c2;		// 身份证号码
	private String c3;		// QQ号码
	private String c4;		// 手机号码
	private String c5;		// 中英文数字下划线
	private String c6;		// 合法字符(a-z A-Z 0-9)
	private String en;		// 英语
	private String zn;		// 汉子
	private String enzn;		// 汉英字符
	
	public TestValidation() {
		super();
	}

	public TestValidation(String id){
		super(id);
	}

	@NotNull(message="浮点数字不能为空")
	@Min(value=(long)20.1,message="浮点数字的最小值不能小于20.1")
	@Max(value=(long)69.3,message="浮点数字的最大值不能超过69.3")
	@ExcelField(title="浮点数字", align=2, sort=1)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@NotNull(message="整数不能为空")
	@Min(value=10,message="整数的最小值不能小于10")
	@Max(value=30,message="整数的最大值不能超过30")
	@ExcelField(title="整数", align=2, sort=2)
	public Integer getNum2() {
		return num2;
	}

	public void setNum2(Integer num2) {
		this.num2 = num2;
	}
	
	@Length(min=5, max=65, message="字符串长度必须介于 5 和 65 之间")
	@ExcelField(title="字符串", align=2, sort=3)
	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
	
	@Length(min=10, max=60, message="邮件长度必须介于 10 和 60 之间")
	@Email(message="邮件必须为合法邮箱")
	@ExcelField(title="邮件", align=2, sort=4)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=10, max=30, message="网址长度必须介于 10 和 30 之间")
	@URL(message="网址必须为合法网址")
	@ExcelField(title="网址", align=2, sort=5)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="日期不能为空")
	@ExcelField(title="日期", align=2, sort=6)
	public Date getNewDate() {
		return newDate;
	}

	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}
	
	@ExcelField(title="浮点数小于等于0", align=2, sort=8)
	public String getC1() {
		return c1;
	}

	public void setC1(String c1) {
		this.c1 = c1;
	}
	
	@ExcelField(title="身份证号码", align=2, sort=9)
	public String getC2() {
		return c2;
	}

	public void setC2(String c2) {
		this.c2 = c2;
	}
	
	@ExcelField(title="QQ号码", align=2, sort=10)
	public String getC3() {
		return c3;
	}

	public void setC3(String c3) {
		this.c3 = c3;
	}
	
	@ExcelField(title="手机号码", align=2, sort=11)
	public String getC4() {
		return c4;
	}

	public void setC4(String c4) {
		this.c4 = c4;
	}
	
	@ExcelField(title="中英文数字下划线", align=2, sort=12)
	public String getC5() {
		return c5;
	}

	public void setC5(String c5) {
		this.c5 = c5;
	}
	
	@ExcelField(title="合法字符(a-z A-Z 0-9)", align=2, sort=13)
	public String getC6() {
		return c6;
	}

	public void setC6(String c6) {
		this.c6 = c6;
	}
	
	@ExcelField(title="英语", align=2, sort=14)
	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}
	
	@ExcelField(title="汉子", align=2, sort=15)
	public String getZn() {
		return zn;
	}

	public void setZn(String zn) {
		this.zn = zn;
	}
	
	@ExcelField(title="汉英字符", align=2, sort=16)
	public String getEnzn() {
		return enzn;
	}

	public void setEnzn(String enzn) {
		this.enzn = enzn;
	}
	
}