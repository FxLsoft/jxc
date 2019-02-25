/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.jxc.entity.OperOrderPay;

/**
 * 付款记录MAPPER接口
 * @author FxLsoft
 * @version 2019-02-13
 */
@MyBatisMapper
public interface OperOrderPayMapper extends BaseMapper<OperOrderPay> {
	
	List<OperOrderPay> findListByWhere(@Param("storeId") String storeId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}