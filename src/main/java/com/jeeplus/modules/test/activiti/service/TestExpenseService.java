/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.activiti.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.test.activiti.entity.TestExpense;
import com.jeeplus.modules.test.activiti.mapper.TestExpenseMapper;

/**
 * 报销申请Service
 * @author 刘高峰
 * @version 2018-06-16
 */
@Service
@Transactional(readOnly = true)
public class TestExpenseService extends CrudService<TestExpenseMapper, TestExpense> {

	public TestExpense get(String id) {
		return super.get(id);
	}
	
	public List<TestExpense> findList(TestExpense testExpense) {
		return super.findList(testExpense);
	}
	
	public Page<TestExpense> findPage(Page<TestExpense> page, TestExpense testExpense) {
		return super.findPage(page, testExpense);
	}
	
	@Transactional(readOnly = false)
	public void save(TestExpense testExpense) {
		super.save(testExpense);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestExpense testExpense) {
		super.delete(testExpense);
	}
	
}