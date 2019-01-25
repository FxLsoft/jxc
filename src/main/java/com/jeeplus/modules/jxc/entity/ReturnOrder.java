/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import javax.validation.constraints.NotNull;
import com.jeeplus.modules.jxc.entity.SaleOrder;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 退货单Entity
 * @author 周涛
 * @version 2018-12-24
 */
public class ReturnOrder extends DataEntity<ReturnOrder> {
	
	private static final long serialVersionUID = 1L;
	private String no;		// 单号
	private Double sum;		// 金额
	private SaleOrder saleOrder;		// 销售单号
	private Integer status;		// 审核状态
	private Date approveDate;		// 审核时间
	private Date cancelDate;		// 作废时间
	private String returnName;		// 退货人姓名
	private String returnPhone;		// 退货人联系方式
	private List<ReturnOrderDetail> returnOrderDetailList = Lists.newArrayList();		// 子表列表
	
	public ReturnOrder() {
		super();
	}

	public ReturnOrder(String id){
		super(id);
	}

	@ExcelField(title="单号", align=2, sort=1)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@NotNull(message="金额不能为空")
	@ExcelField(title="金额", align=2, sort=2)
	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}
	
	@NotNull(message="销售单号不能为空")
	@ExcelField(title="销售单号", fieldType=SaleOrder.class, value="saleOrder.no", align=2, sort=3)
	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}
	
	@ExcelField(title="审核状态", align=2, sort=4)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审核时间", align=2, sort=5)
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="作废时间", align=2, sort=6)
	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	
	@ExcelField(title="退货人姓名", align=2, sort=7)
	public String getReturnName() {
		return returnName;
	}

	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}
	
	@ExcelField(title="退货人联系方式", align=2, sort=8)
	public String getReturnPhone() {
		return returnPhone;
	}

	public void setReturnPhone(String returnPhone) {
		this.returnPhone = returnPhone;
	}
	
	public List<ReturnOrderDetail> getReturnOrderDetailList() {
		return returnOrderDetailList;
	}

	public void setReturnOrderDetailList(List<ReturnOrderDetail> returnOrderDetailList) {
		this.returnOrderDetailList = returnOrderDetailList;
	}
}