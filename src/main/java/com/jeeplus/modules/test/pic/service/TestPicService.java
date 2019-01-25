/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.pic.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.test.pic.entity.TestPic;
import com.jeeplus.modules.test.pic.mapper.TestPicMapper;

/**
 * 图片管理Service
 * @author lgf
 * @version 2018-06-12
 */
@Service
@Transactional(readOnly = true)
public class TestPicService extends CrudService<TestPicMapper, TestPic> {

	public TestPic get(String id) {
		return super.get(id);
	}
	
	public List<TestPic> findList(TestPic testPic) {
		return super.findList(testPic);
	}
	
	public Page<TestPic> findPage(Page<TestPic> page, TestPic testPic) {
		return super.findPage(page, testPic);
	}
	
	@Transactional(readOnly = false)
	public void save(TestPic testPic) {
		super.save(testPic);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestPic testPic) {
		super.delete(testPic);
	}
	
}