/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 单据Entity
 * @author FxLsoft
 * @version 2019-02-11
 */
public class OperOrder extends DataEntity<OperOrder> {
	
	private static final long serialVersionUID = 1L;
	private String no;		// 编号
	private String type;		// 单据类型（0：入库，1：出库，2：盘点）
	private String status;		// 单据状态（0：保存，1：提交，2：作废，3：完成）
	private String source;		// 单据来源（0：直接入库，1：盘点入库，2：退货入库，3、电子秤零售，4、零售出库，5、批发出库）
	private Double totalPrice;		// 总计
	private Double realPrice;		// 实际总额
	private Double realPay;		// 实付
	private Double beginTotalPrice;		// 开始 总计
	private Double endTotalPrice;		// 结束 总计
	private List<OperOrderDetail> operOrderDetailList = Lists.newArrayList();		// 子表列表
	
	public OperOrder() {
		super();
	}

	public OperOrder(String id){
		super(id);
	}

	@ExcelField(title="编号", align=2, sort=1)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@ExcelField(title="单据类型（0：入库，1：出库，2：盘点）", dictType="order_type", align=2, sort=2)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="单据状态（0：保存，1：提交，2：作废，3：完成）", dictType="order_status", align=2, sort=3)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="单据来源（0：直接入库，1：盘点入库，2：退货入库，3、电子秤零售，4、零售出库，5、批发出库）", dictType="order_from", align=2, sort=4)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@ExcelField(title="总计", align=2, sort=5)
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@ExcelField(title="实际总额", align=2, sort=6)
	public Double getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}
	
	@ExcelField(title="实付", align=2, sort=7)
	public Double getRealPay() {
		return realPay;
	}

	public void setRealPay(Double realPay) {
		this.realPay = realPay;
	}
	
	public Double getBeginTotalPrice() {
		return beginTotalPrice;
	}

	public void setBeginTotalPrice(Double beginTotalPrice) {
		this.beginTotalPrice = beginTotalPrice;
	}
	
	public Double getEndTotalPrice() {
		return endTotalPrice;
	}

	public void setEndTotalPrice(Double endTotalPrice) {
		this.endTotalPrice = endTotalPrice;
	}
		
	public List<OperOrderDetail> getOperOrderDetailList() {
		return operOrderDetailList;
	}

	public void setOperOrderDetailList(List<OperOrderDetail> operOrderDetailList) {
		this.operOrderDetailList = operOrderDetailList;
	}
}