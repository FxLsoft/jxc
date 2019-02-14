/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.jxc.entity.OperOrder;

/**
 * 单据MAPPER接口
 * @author FxLsoft
 * @version 2019-02-13
 */
@MyBatisMapper
public interface OperOrderMapper extends BaseMapper<OperOrder> {
	@Update("UPDATE c_oper_order SET status = '${status}' WHERE id = '${id}'")
	int updateOrderStatus(@Param("id") String id, @Param("status") String status);
}