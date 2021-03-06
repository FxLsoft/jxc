/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.jxc.entity.Customer;

/**
 * 客户管理MAPPER接口
 * @author FxLsoft
 * @version 2019-02-23
 */
@MyBatisMapper
public interface CustomerMapper extends BaseMapper<Customer> {
	
}