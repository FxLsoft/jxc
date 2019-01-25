/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.activiti.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.test.activiti.entity.TestAudit;

/**
 * 薪酬调整申请MAPPER接口
 * @author 刘高峰
 * @version 2018-06-16
 */
@MyBatisMapper
public interface TestAuditMapper extends BaseMapper<TestAudit> {
	
}