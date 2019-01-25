/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.note.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.test.note.entity.TestNote;

/**
 * 富文本测试MAPPER接口
 * @author liugf
 * @version 2018-06-12
 */
@MyBatisMapper
public interface TestNoteMapper extends BaseMapper<TestNote> {
	
}