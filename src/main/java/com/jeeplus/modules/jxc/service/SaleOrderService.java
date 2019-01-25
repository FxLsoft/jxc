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
import com.jeeplus.modules.jxc.entity.SaleOrder;
import com.jeeplus.modules.jxc.mapper.SaleOrderMapper;
import com.jeeplus.modules.jxc.entity.SaleOrderDetail;
import com.jeeplus.modules.jxc.mapper.SaleOrderDetailMapper;

/**
 * 销售单Service
 * @author 周涛
 * @version 2018-12-24
 */
@Service
@Transactional(readOnly = true)
public class SaleOrderService extends CrudService<SaleOrderMapper, SaleOrder> {

	@Autowired
	private SaleOrderDetailMapper saleOrderDetailMapper;
	
	public SaleOrder get(String id) {
		SaleOrder saleOrder = super.get(id);
		saleOrder.setSaleOrderDetailList(saleOrderDetailMapper.findList(new SaleOrderDetail(saleOrder)));
		return saleOrder;
	}
	
	public List<SaleOrder> findList(SaleOrder saleOrder) {
		return super.findList(saleOrder);
	}
	
	public Page<SaleOrder> findPage(Page<SaleOrder> page, SaleOrder saleOrder) {
		return super.findPage(page, saleOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(SaleOrder saleOrder) {
		super.save(saleOrder);
		for (SaleOrderDetail saleOrderDetail : saleOrder.getSaleOrderDetailList()){
			if (saleOrderDetail.getId() == null){
				continue;
			}
			if (SaleOrderDetail.DEL_FLAG_NORMAL.equals(saleOrderDetail.getDelFlag())){
				if (StringUtils.isBlank(saleOrderDetail.getId())){
					saleOrderDetail.setSaleOrder(saleOrder);
					saleOrderDetail.preInsert();
					saleOrderDetailMapper.insert(saleOrderDetail);
				}else{
					saleOrderDetail.preUpdate();
					saleOrderDetailMapper.update(saleOrderDetail);
				}
			}else{
				saleOrderDetailMapper.delete(saleOrderDetail);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(SaleOrder saleOrder) {
		super.delete(saleOrder);
		saleOrderDetailMapper.delete(new SaleOrderDetail(saleOrder));
	}
	
}