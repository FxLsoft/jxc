/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.activiti.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.test.activiti.entity.OALeave;
import com.jeeplus.modules.test.activiti.mapper.OALeaveMapper;

/**
 * 请假申请Service
 * @author 刘高峰
 * @version 2018-06-16
 */
@Service
@Transactional(readOnly = true)
public class OALeaveService extends CrudService<OALeaveMapper, OALeave> {

	public OALeave get(String id) {
		return super.get(id);
	}
	
	public List<OALeave> findList(OALeave oALeave) {
		return super.findList(oALeave);
	}
	
	public Page<OALeave> findPage(Page<OALeave> page, OALeave oALeave) {
		return super.findPage(page, oALeave);
	}
	
	@Transactional(readOnly = false)
	public void save(OALeave oALeave) {
		super.save(oALeave);
	}
	
	@Transactional(readOnly = false)
	public void delete(OALeave oALeave) {
		super.delete(oALeave);
	}
	
}