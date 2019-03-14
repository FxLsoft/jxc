/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Select;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.jxc.entity.Report;

/**
 * 财务报表MAPPER接口
 * @author FxLsoft
 * @version 2019-03-14
 */
@MyBatisMapper
public interface ReportMapper extends BaseMapper<Report> {
	@Select("SELECT max(r.create_date) FROM c_report r;")
	Date getLastCreateDate();
}