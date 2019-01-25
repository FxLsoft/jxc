/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.manytomany.entity;

import com.jeeplus.modules.test.manytomany.entity.Student;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.test.manytomany.entity.Course;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 学生课程记录Entity
 * @author lgf
 * @version 2018-06-12
 */
public class StudentCourse extends DataEntity<StudentCourse> {
	
	private static final long serialVersionUID = 1L;
	private Student student;		// 学生
	private Course course;		// 课程
	private Double score;		// 分数
	
	public StudentCourse() {
		super();
	}

	public StudentCourse(String id){
		super(id);
	}

	@NotNull(message="学生不能为空")
	@ExcelField(title="学生", fieldType=Student.class, value="student.name", align=2, sort=1)
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	@NotNull(message="课程不能为空")
	@ExcelField(title="课程", fieldType=Course.class, value="course.name", align=2, sort=2)
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	@NotNull(message="分数不能为空")
	@ExcelField(title="分数", align=2, sort=3)
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
	
}