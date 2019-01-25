/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 销售单Entity
 * @author 周涛
 * @version 2018-12-24
 */
public class SaleOrder extends DataEntity<SaleOrder> {
	
	private static final long serialVersionUID = 1L;
	private String no;		// 单号
	private Double sum;		// 总金额
	private Integer type;		// 类别
	private Integer status;		// 审核状态
	private Date approveDate;		// 审核时间
	private Date cancelDate;		// 作废时间
	private Double beginSum;		// 开始 总金额
	private Double endSum;		// 结束 总金额
	private List<SaleOrderDetail> saleOrderDetailList = Lists.newArrayList();		// 子表列表
	
	public SaleOrder() {
		super();
	}

	public SaleOrder(String id){
		super(id);
	}

	@ExcelField(title="单号", align=2, sort=1)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@NotNull(message="总金额不能为空")
	@ExcelField(title="总金额", align=2, sort=2)
	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}
	
	@NotNull(message="类别不能为空")
	@ExcelField(title="类别", dictType="sale_type", align=2, sort=3)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="审核状态", dictType="order_status", align=2, sort=4)
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
	
	public Double getBeginSum() {
		return beginSum;
	}

	public void setBeginSum(Double beginSum) {
		this.beginSum = beginSum;
	}
	
	public Double getEndSum() {
		return endSum;
	}

	public void setEndSum(Double endSum) {
		this.endSum = endSum;
	}
		
	public List<SaleOrderDetail> getSaleOrderDetailList() {
		return saleOrderDetailList;
	}
	
	public List<SaleOrderDetail> getUniqueSaleOrderDetailList() {
		// 合并
		List<SaleOrderDetail> formateList = Lists.newArrayList();
		for (int i = 0, size = saleOrderDetailList.size(); i < size; i++) {
			Boolean has = false;
			for (int k = 0, length = formateList.size(); k < length; k++) {
				if (saleOrderDetailList.get(i).getProduct().getId() == formateList.get(k).getProduct().getId()) {
					formateList.get(k).setQuantity(formateList.get(k).getQuantity() + formateList.get(i).getQuantity());
					has = true;
					break;
				}
			}
			if (!has) {
				formateList.add(saleOrderDetailList.get(i));
			}
		}
		return formateList;
	}

	public void setSaleOrderDetailList(List<SaleOrderDetail> saleOrderDetailList) {
		this.saleOrderDetailList = saleOrderDetailList;
	}
}