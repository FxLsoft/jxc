/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.manytoone.mapper;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.test.manytoone.entity.Category;

/**
 * 商品类型MAPPER接口
 * @author liugf
 * @version 2018-06-12
 */
@MyBatisMapper
public interface CategoryMapper extends TreeMapper<Category> {
	
}