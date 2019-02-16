/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jxc.entity.Balance;
import com.jeeplus.modules.jxc.mapper.BalanceMapper;

/**
 * 电子秤管理Service
 * @author FxLsoft
 * @version 2019-02-15
 */
@Service
@Transactional(readOnly = true)
public class BalanceService extends CrudService<BalanceMapper, Balance> {

	public Balance get(String id) {
		return super.get(id);
	}
	
	public List<Balance> findList(Balance balance) {
		return super.findList(balance);
	}
	
	public Page<Balance> findPage(Page<Balance> page, Balance balance) {
		return super.findPage(page, balance);
	}
	
	@Transactional(readOnly = false)
	public void save(Balance balance) {
		super.save(balance);
	}
	
	@Transactional(readOnly = false)
	public void delete(Balance balance) {
		super.delete(balance);
	}
	
}