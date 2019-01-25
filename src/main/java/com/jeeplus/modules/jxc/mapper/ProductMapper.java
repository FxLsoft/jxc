/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.jxc.entity.Product;

/**
 * 商品信息MAPPER接口
 * @author FxLsoft
 * @version 2018-12-22
 */
@MyBatisMapper
public interface ProductMapper extends BaseMapper<Product> {
	List<Product> queryProductByKey(@Param(value="value")String value, @Param("retail") Integer retail, @Param("start") int start, @Param("end") int end, @Param("company") String company);
	
	Integer countProductByKey(@Param(value="value")String value, @Param("retail") Integer retail, @Param("company") String company);
}