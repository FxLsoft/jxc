/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jxc.entity.Employee;
import com.jeeplus.modules.jxc.mapper.EmployeeMapper;

/**
 * 员工信息Service
 * @author FxLsoft
 * @version 2018-12-22
 */
@Service
@Transactional(readOnly = true)
public class EmployeeService extends CrudService<EmployeeMapper, Employee> {

	public Employee get(String id) {
		return super.get(id);
	}
	
	public List<Employee> findList(Employee employee) {
		return super.findList(employee);
	}
	
	public Page<Employee> findPage(Page<Employee> page, Employee employee) {
		return super.findPage(page, employee);
	}
	
	@Transactional(readOnly = false)
	public void save(Employee employee) {
		super.save(employee);
	}
	
	@Transactional(readOnly = false)
	public void delete(Employee employee) {
		super.delete(employee);
	}
	
}