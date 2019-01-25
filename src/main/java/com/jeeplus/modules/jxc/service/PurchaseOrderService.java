/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.jxc.entity.PurchaseOrder;
import com.jeeplus.modules.jxc.mapper.PurchaseOrderMapper;
import com.jeeplus.modules.jxc.entity.PurchaseOrderDetail;
import com.jeeplus.modules.jxc.mapper.PurchaseOrderDetailMapper;

/**
 * 采购订单Service
 * @author FxLsoft
 * @version 2018-12-22
 */
@Service
@Transactional(readOnly = true)
public class PurchaseOrderService extends CrudService<PurchaseOrderMapper, PurchaseOrder> {

	@Autowired
	private PurchaseOrderDetailMapper purchaseOrderDetailMapper;
	
	public PurchaseOrder get(String id) {
		PurchaseOrder purchaseOrder = super.get(id);
		purchaseOrder.setPurchaseOrderDetailList(purchaseOrderDetailMapper.findList(new PurchaseOrderDetail(purchaseOrder)));
		return purchaseOrder;
	}
	
	public List<PurchaseOrder> findList(PurchaseOrder purchaseOrder) {
		return super.findList(purchaseOrder);
	}
	
	public Page<PurchaseOrder> findPage(Page<PurchaseOrder> page, PurchaseOrder purchaseOrder) {
		return super.findPage(page, purchaseOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(PurchaseOrder purchaseOrder) {
		super.save(purchaseOrder);
		for (PurchaseOrderDetail purchaseOrderDetail : purchaseOrder.getPurchaseOrderDetailList()){
			if (purchaseOrderDetail.getId() == null){
				continue;
			}
			if (PurchaseOrderDetail.DEL_FLAG_NORMAL.equals(purchaseOrderDetail.getDelFlag())){
				if (StringUtils.isBlank(purchaseOrderDetail.getId())){
					purchaseOrderDetail.setPurchaseOrder(purchaseOrder);
					purchaseOrderDetail.preInsert();
					purchaseOrderDetailMapper.insert(purchaseOrderDetail);
				}else{
					purchaseOrderDetail.preUpdate();
					purchaseOrderDetailMapper.update(purchaseOrderDetail);
				}
			}else{
				purchaseOrderDetailMapper.delete(purchaseOrderDetail);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaseOrder purchaseOrder) {
		super.delete(purchaseOrder);
		purchaseOrderDetailMapper.delete(new PurchaseOrderDetail(purchaseOrder));
	}
	
}