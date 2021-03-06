/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.mapper;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.jxc.entity.Store;

/**
 * 门店MAPPER接口
 * @author FxLsoft
 * @version 2019-02-11
 */
@MyBatisMapper
public interface StoreMapper extends BaseMapper<Store> {
	List<Store> getAllStore();
}