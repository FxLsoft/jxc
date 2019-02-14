package com.jeeplus.modules.jxc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jxc.entity.OperOrderPay;
import com.jeeplus.modules.jxc.mapper.OperOrderPayMapper;

@Service
@Transactional(readOnly = true)
public class OperOrderPayService extends CrudService<OperOrderPayMapper, OperOrderPay>{

	public OperOrderPay get(String id) {
		return super.get(id);
	}
	
	public List<OperOrderPay> findList(OperOrderPay operOrderPay) {
		return super.findList(operOrderPay);
	}
	
	public Page<OperOrderPay> findPage(Page<OperOrderPay> page, OperOrderPay operOrderPay) {
		return super.findPage(page, operOrderPay);
	}
	
	@Transactional(readOnly = false)
	public void save(OperOrderPay operOrderPay) {
		super.save(operOrderPay);
	}
	
	@Transactional(readOnly = false)
	public void delete(OperOrderPay operOrderPay) {
		super.delete(operOrderPay);
	}
}
