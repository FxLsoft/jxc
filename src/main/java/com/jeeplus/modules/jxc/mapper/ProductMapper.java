/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.mapper;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.jxc.entity.Product;

/**
 * 商品MAPPER接口
 * @author FxLsoft
 * @version 2019-02-11
 */
@MyBatisMapper
public interface ProductMapper extends BaseMapper<Product> {
	Product getProductByWeightNo(@Param("weightNo") String weightNo, @Param("storeId") String storeId);
}