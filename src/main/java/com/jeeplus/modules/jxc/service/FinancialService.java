/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jxc.entity.Financial;
import com.jeeplus.modules.jxc.mapper.FinancialMapper;

/**
 * 报表Service
 * @author 周涛
 * @version 2018-12-22
 */
@Service
@Transactional(readOnly = true)
public class FinancialService extends CrudService<FinancialMapper, Financial> {
	
	@Autowired
	private FinancialMapper financialMapper;

	public Financial get(String id) {
		return super.get(id);
	}
	
	public List<Financial> findList(Financial financial) {
		return super.findList(financial);
	}
	
	public Page<Financial> findPage(Page<Financial> page, Financial financial) {
		return super.findPage(page, financial);
	}
	
	@Transactional(readOnly = false)
	public void save(Financial financial) {
		super.save(financial);
	}
	
	@Transactional(readOnly = false)
	public void insert(Financial financial) {
		financialMapper.insert(financial);
	}
	
	@Transactional(readOnly = false)
	public void delete(Financial financial) {
		super.delete(financial);
	}
	
	public Financial queryFinancialByTime(Date beginDate, Date endDate) {
		Financial financial = financialMapper.queryFinancialByTime(beginDate, endDate);
		if (financial != null) {
			financial.setBillAmount(financial.getStockBillAmount() + financial.getReturnBillAmount());
		}
		return financial;
	}
}