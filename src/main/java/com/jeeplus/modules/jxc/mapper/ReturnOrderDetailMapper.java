/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.mapper;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.jxc.entity.ReturnOrderDetail;

/**
 * 退货详情MAPPER接口
 * @author 周涛
 * @version 2018-12-24
 */
@MyBatisMapper
public interface ReturnOrderDetailMapper extends BaseMapper<ReturnOrderDetail> {
	public int deleteByOrderNo(@Param("no") String no);
}