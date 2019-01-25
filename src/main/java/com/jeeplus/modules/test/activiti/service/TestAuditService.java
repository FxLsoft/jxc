/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.activiti.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.test.activiti.entity.TestAudit;
import com.jeeplus.modules.test.activiti.mapper.TestAuditMapper;

/**
 * 薪酬调整申请Service
 * @author 刘高峰
 * @version 2018-06-16
 */
@Service
@Transactional(readOnly = true)
public class TestAuditService extends CrudService<TestAuditMapper, TestAudit> {

	public TestAudit get(String id) {
		return super.get(id);
	}
	
	public List<TestAudit> findList(TestAudit testAudit) {
		return super.findList(testAudit);
	}
	
	public Page<TestAudit> findPage(Page<TestAudit> page, TestAudit testAudit) {
		return super.findPage(page, testAudit);
	}
	
	@Transactional(readOnly = false)
	public void save(TestAudit testAudit) {
		super.save(testAudit);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestAudit testAudit) {
		super.delete(testAudit);
	}
	
}