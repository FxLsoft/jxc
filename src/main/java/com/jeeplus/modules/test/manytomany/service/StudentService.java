/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.manytomany.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.test.manytomany.entity.Student;
import com.jeeplus.modules.test.manytomany.mapper.StudentMapper;

/**
 * 学生Service
 * @author lgf
 * @version 2018-06-12
 */
@Service
@Transactional(readOnly = true)
public class StudentService extends CrudService<StudentMapper, Student> {

	public Student get(String id) {
		return super.get(id);
	}
	
	public List<Student> findList(Student student) {
		return super.findList(student);
	}
	
	public Page<Student> findPage(Page<Student> page, Student student) {
		return super.findPage(page, student);
	}
	
	@Transactional(readOnly = false)
	public void save(Student student) {
		super.save(student);
	}
	
	@Transactional(readOnly = false)
	public void delete(Student student) {
		super.delete(student);
	}
	
}