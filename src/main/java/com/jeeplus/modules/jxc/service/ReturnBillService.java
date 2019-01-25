/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jxc.entity.ReturnBill;
import com.jeeplus.modules.jxc.mapper.ReturnBillMapper;

/**
 * 退货单据Service
 * @author FxLsoft
 * @version 2018-12-23
 */
@Service
@Transactional(readOnly = true)
public class ReturnBillService extends CrudService<ReturnBillMapper, ReturnBill> {

	public ReturnBill get(String id) {
		return super.get(id);
	}
	
	public List<ReturnBill> findList(ReturnBill returnBill) {
		return super.findList(returnBill);
	}
	
	public Page<ReturnBill> findPage(Page<ReturnBill> page, ReturnBill returnBill) {
		return super.findPage(page, returnBill);
	}
	
	@Transactional(readOnly = false)
	public void save(ReturnBill returnBill) {
		super.save(returnBill);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReturnBill returnBill) {
		super.delete(returnBill);
	}
	
}