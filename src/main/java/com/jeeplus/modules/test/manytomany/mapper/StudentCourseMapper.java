/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.manytomany.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.test.manytomany.entity.StudentCourse;

/**
 * 学生课程记录MAPPER接口
 * @author lgf
 * @version 2018-06-12
 */
@MyBatisMapper
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {
	
}