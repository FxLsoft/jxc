/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.jxc.entity.Financial;

/**
 * 报表MAPPER接口
 * @author 周涛
 * @version 2018-12-22
 */
@MyBatisMapper
public interface FinancialMapper extends BaseMapper<Financial> {
	Financial queryFinancialByTime(@Param("beginDate") Date beginDate, @Param("endDate")Date endDate);
}