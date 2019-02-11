/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.mapper;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.jxc.entity.Category;

/**
 * 商品类型MAPPER接口
 * @author FxLsoft
 * @version 2019-02-11
 */
@MyBatisMapper
public interface CategoryMapper extends TreeMapper<Category> {
	
}