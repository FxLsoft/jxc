/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jxc.entity.Report;
import com.jeeplus.modules.jxc.mapper.ReportMapper;

/**
 * 财务报表Service
 * @author FxLsoft
 * @version 2019-02-23
 */
@Service
@Transactional(readOnly = true)
public class ReportService extends CrudService<ReportMapper, Report> {

	public Report get(String id) {
		return super.get(id);
	}
	
	public List<Report> findList(Report report) {
		return super.findList(report);
	}
	
	public Page<Report> findPage(Page<Report> page, Report report) {
		return super.findPage(page, report);
	}
	
	@Transactional(readOnly = false)
	public void save(Report report) {
		super.save(report);
	}
	
	@Transactional(readOnly = false)
	public void delete(Report report) {
		super.delete(report);
	}
	
}