/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.jxc.entity.Storage;

/**
 * 库存MAPPER接口
 * @author FxLsoft
 * @version 2019-02-12
 */
@MyBatisMapper
public interface StorageMapper extends BaseMapper<Storage> {
	public int deleteByProductId(@Param(value="productId")String productId);
	
	List<Storage> getStorageList(Storage storage);
}