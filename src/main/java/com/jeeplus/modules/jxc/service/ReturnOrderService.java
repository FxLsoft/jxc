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
import com.jeeplus.modules.jxc.entity.ReturnOrder;
import com.jeeplus.modules.jxc.mapper.ReturnOrderMapper;
import com.jeeplus.modules.jxc.entity.ReturnOrderDetail;
import com.jeeplus.modules.jxc.mapper.ReturnOrderDetailMapper;

/**
 * 退货单Service
 * @author 周涛
 * @version 2018-12-24
 */
@Service
@Transactional(readOnly = true)
public class ReturnOrderService extends CrudService<ReturnOrderMapper, ReturnOrder> {

	@Autowired
	private ReturnOrderDetailMapper returnOrderDetailMapper;
	
	public ReturnOrder get(String id) {
		ReturnOrder returnOrder = super.get(id);
		returnOrder.setReturnOrderDetailList(returnOrderDetailMapper.findList(new ReturnOrderDetail(returnOrder)));
		return returnOrder;
	}
	
	public List<ReturnOrder> findList(ReturnOrder returnOrder) {
		return super.findList(returnOrder);
	}
	
	public Page<ReturnOrder> findPage(Page<ReturnOrder> page, ReturnOrder returnOrder) {
		return super.findPage(page, returnOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(ReturnOrder returnOrder) {
		super.save(returnOrder);
		for (ReturnOrderDetail returnOrderDetail : returnOrder.getReturnOrderDetailList()){
			if (returnOrderDetail.getId() == null){
				continue;
			}
			if (ReturnOrderDetail.DEL_FLAG_NORMAL.equals(returnOrderDetail.getDelFlag())){
				if (StringUtils.isBlank(returnOrderDetail.getId())){
					returnOrderDetail.setReturnOrder(returnOrder);
					returnOrderDetail.preInsert();
					returnOrderDetailMapper.insert(returnOrderDetail);
				}else{
					returnOrderDetail.preUpdate();
					returnOrderDetailMapper.update(returnOrderDetail);
				}
			}else{
				returnOrderDetailMapper.delete(returnOrderDetail);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ReturnOrder returnOrder) {
		super.delete(returnOrder);
		returnOrderDetailMapper.delete(new ReturnOrderDetail(returnOrder));
	}
	
}