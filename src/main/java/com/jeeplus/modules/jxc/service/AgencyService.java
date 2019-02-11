/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jxc.entity.Agency;
import com.jeeplus.modules.jxc.mapper.AgencyMapper;

/**
 * 经销商Service
 * @author FxLsoft
 * @version 2019-02-11
 */
@Service
@Transactional(readOnly = true)
public class AgencyService extends CrudService<AgencyMapper, Agency> {

	public Agency get(String id) {
		return super.get(id);
	}
	
	public List<Agency> findList(Agency agency) {
		return super.findList(agency);
	}
	
	public Page<Agency> findPage(Page<Agency> page, Agency agency) {
		return super.findPage(page, agency);
	}
	
	@Transactional(readOnly = false)
	public void save(Agency agency) {
		super.save(agency);
	}
	
	@Transactional(readOnly = false)
	public void delete(Agency agency) {
		super.delete(agency);
	}
	
}