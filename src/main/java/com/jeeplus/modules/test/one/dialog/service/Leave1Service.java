/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.one.dialog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.test.one.dialog.entity.Leave1;
import com.jeeplus.modules.test.one.dialog.mapper.Leave1Mapper;

/**
 * 请假表单Service
 * @author lgf
 * @version 2018-06-12
 */
@Service
@Transactional(readOnly = true)
public class Leave1Service extends CrudService<Leave1Mapper, Leave1> {

	public Leave1 get(String id) {
		return super.get(id);
	}
	
	public List<Leave1> findList(Leave1 leave1) {
		return super.findList(leave1);
	}
	
	public Page<Leave1> findPage(Page<Leave1> page, Leave1 leave1) {
		return super.findPage(page, leave1);
	}
	
	@Transactional(readOnly = false)
	public void save(Leave1 leave1) {
		super.save(leave1);
	}
	
	@Transactional(readOnly = false)
	public void delete(Leave1 leave1) {
		super.delete(leave1);
	}
	
}