/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.Log;

/**
 * 日志MAPPER接口
 * @author jeeplus
 * @version 2017-05-16
 */
@MyBatisMapper
public interface LogMapper extends BaseMapper<Log> {

	public void empty();
}
