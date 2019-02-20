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
import com.jeeplus.modules.jxc.entity.OperOrder;
import com.jeeplus.modules.jxc.mapper.OperOrderMapper;
import com.jeeplus.modules.jxc.entity.OperOrderDetail;
import com.jeeplus.modules.jxc.mapper.OperOrderDetailMapper;
import com.jeeplus.modules.jxc.entity.OperOrderPay;
import com.jeeplus.modules.jxc.mapper.OperOrderPayMapper;

/**
 * 单据Service
 * @author FxLsoft
 * @version 2019-02-13
 */
@Service
@Transactional(readOnly = true)
public class OperOrderService extends CrudService<OperOrderMapper, OperOrder> {

	@Autowired
	private OperOrderDetailMapper operOrderDetailMapper;
	@Autowired
	private OperOrderPayMapper operOrderPayMapper;
	
	public OperOrder get(String id) {
		OperOrder operOrder = super.get(id);
		operOrder.setOperOrderDetailList(operOrderDetailMapper.findList(new OperOrderDetail(operOrder)));
		operOrder.setOperOrderPayList(operOrderPayMapper.findList(new OperOrderPay(operOrder)));
		return operOrder;
	}
	
	public List<OperOrder> findList(OperOrder operOrder) {
		return super.findList(operOrder);
	}
	
	public Page<OperOrder> findPage(Page<OperOrder> page, OperOrder operOrder) {
		return super.findPage(page, operOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(OperOrder operOrder) {
		super.save(operOrder);
		for (OperOrderDetail operOrderDetail : operOrder.getOperOrderDetailList()){
			operOrderDetail.setOperOrder(operOrder);
			if (OperOrderDetail.DEL_FLAG_NORMAL.equals(operOrderDetail.getDelFlag())){
				if (StringUtils.isBlank(operOrderDetail.getId())){
					operOrderDetail.setOperOrder(operOrder);
					operOrderDetail.preInsert();
					operOrderDetailMapper.insert(operOrderDetail);
				}else{
					operOrderDetail.preUpdate();
					operOrderDetailMapper.update(operOrderDetail);
				}
			}else{
				operOrderDetailMapper.delete(operOrderDetail);
			}
		}
		for (OperOrderPay operOrderPay : operOrder.getOperOrderPayList()){
			if (operOrderPay.getId() == null){
				continue;
			}
			if (OperOrderPay.DEL_FLAG_NORMAL.equals(operOrderPay.getDelFlag())){
				if (StringUtils.isBlank(operOrderPay.getId())){
					operOrderPay.setOperOrder(operOrder);
					operOrderPay.preInsert();
					operOrderPayMapper.insert(operOrderPay);
				}else{
					operOrderPay.preUpdate();
					operOrderPayMapper.update(operOrderPay);
				}
			}else{
				operOrderPayMapper.delete(operOrderPay);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(OperOrder operOrder) {
		super.delete(operOrder);
		operOrderDetailMapper.delete(new OperOrderDetail(operOrder));
		operOrderPayMapper.delete(new OperOrderPay(operOrder));
	}
	
	@Transactional(readOnly = false)
	public int updateOrderStatus(String id, String status) {
		return mapper.updateOrderStatus(id, status);
	}
}