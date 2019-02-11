/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 电子秤销售Entity
 * @author FxLsoft
 * @version 2019-02-11
 */
public class BalanceSale extends DataEntity<BalanceSale> {
	
	private static final long serialVersionUID = 1L;
	private String balanceNo;		// 电子秤编号
	private String weightNo;		// 商品计重编号
	private Double amount;		// 数量
	
	public BalanceSale() {
		super();
	}

	public BalanceSale(String id){
		super(id);
	}

	@ExcelField(title="电子秤编号", align=2, sort=1)
	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}
	
	@ExcelField(title="商品计重编号", align=2, sort=2)
	public String getWeightNo() {
		return weightNo;
	}

	public void setWeightNo(String weightNo) {
		this.weightNo = weightNo;
	}
	
	@ExcelField(title="数量", align=2, sort=3)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}