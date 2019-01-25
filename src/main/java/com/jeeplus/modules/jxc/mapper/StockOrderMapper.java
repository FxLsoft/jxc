/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.jxc.entity.StockOrder;

/**
 * 入库单MAPPER接口
 * @author 周涛
 * @version 2018-12-25
 */
@MyBatisMapper
public interface StockOrderMapper extends BaseMapper<StockOrder> {
	
}