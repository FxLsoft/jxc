/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jxc.entity.RevenueOrder;
import com.jeeplus.modules.jxc.mapper.RevenueOrderMapper;

/**
 * 应收单Service
 * @author 周涛
 * @version 2018-12-23
 */
@Service
@Transactional(readOnly = true)
public class RevenueOrderService extends CrudService<RevenueOrderMapper, RevenueOrder> {

	public RevenueOrder get(String id) {
		return super.get(id);
	}
	
	public List<RevenueOrder> findList(RevenueOrder revenueOrder) {
		return super.findList(revenueOrder);
	}
	
	public Page<RevenueOrder> findPage(Page<RevenueOrder> page, RevenueOrder revenueOrder) {
		return super.findPage(page, revenueOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(RevenueOrder revenueOrder) {
		super.save(revenueOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(RevenueOrder revenueOrder) {
		super.delete(revenueOrder);
	}
	
}