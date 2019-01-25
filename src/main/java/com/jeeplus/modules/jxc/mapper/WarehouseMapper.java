/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.jxc.entity.Warehouse;

/**
 * 商品仓库MAPPER接口
 * @author 周涛
 * @version 2018-12-25
 */
@MyBatisMapper
public interface WarehouseMapper extends BaseMapper<Warehouse> {
	public Double sumProduct(@Param("id") String id, @Param("isRetail") Integer isRetail, @Param("company") String company);
	
	public List<Warehouse> selectWarehouseById(@Param("id") String id, @Param("isRetail") Integer isRetail, @Param("company") String company);
}