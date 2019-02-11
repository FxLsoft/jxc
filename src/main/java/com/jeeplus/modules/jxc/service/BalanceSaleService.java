/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jxc.entity.BalanceSale;
import com.jeeplus.modules.jxc.mapper.BalanceSaleMapper;

/**
 * 电子秤销售Service
 * @author FxLsoft
 * @version 2019-02-11
 */
@Service
@Transactional(readOnly = true)
public class BalanceSaleService extends CrudService<BalanceSaleMapper, BalanceSale> {

	public BalanceSale get(String id) {
		return super.get(id);
	}
	
	public List<BalanceSale> findList(BalanceSale balanceSale) {
		return super.findList(balanceSale);
	}
	
	public Page<BalanceSale> findPage(Page<BalanceSale> page, BalanceSale balanceSale) {
		return super.findPage(page, balanceSale);
	}
	
	@Transactional(readOnly = false)
	public void save(BalanceSale balanceSale) {
		super.save(balanceSale);
	}
	
	@Transactional(readOnly = false)
	public void delete(BalanceSale balanceSale) {
		super.delete(balanceSale);
	}
	
}