/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jxc.entity.BillOrder;
import com.jeeplus.modules.jxc.mapper.BillOrderMapper;

/**
 * 应付单Service
 * @author 周涛
 * @version 2018-12-22
 */
@Service
@Transactional(readOnly = true)
public class BillOrderService extends CrudService<BillOrderMapper, BillOrder> {

	public BillOrder get(String id) {
		return super.get(id);
	}
	
	public List<BillOrder> findList(BillOrder billOrder) {
		return super.findList(billOrder);
	}
	
	public Page<BillOrder> findPage(Page<BillOrder> page, BillOrder billOrder) {
		return super.findPage(page, billOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(BillOrder billOrder) {
		super.save(billOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(BillOrder billOrder) {
		super.delete(billOrder);
	}
	
}