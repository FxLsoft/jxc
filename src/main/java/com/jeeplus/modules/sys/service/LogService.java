/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.Log;
import com.jeeplus.modules.sys.mapper.LogMapper;

/**
 * 日志Service
 * @author jeeplus
 * @version 2017-05-16
 */
@Service
@Transactional(readOnly = true)
public class LogService extends CrudService<LogMapper, Log> {

	@Autowired
	private LogMapper logMapper;
	
	public Page<Log> findPage(Page<Log> page, Log log) {
		
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null){
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null){
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		
		return super.findPage(page, log);
		
	}
	
	/**
	 * 删除全部数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void empty(){
		
		logMapper.empty();
	}
	
}
