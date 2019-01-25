/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.tree.form.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.test.tree.form.entity.TestTree2;
import com.jeeplus.modules.test.tree.form.mapper.TestTree2Mapper;

/**
 * 组织机构Service
 * @author liugf
 * @version 2018-06-12
 */
@Service
@Transactional(readOnly = true)
public class TestTree2Service extends TreeService<TestTree2Mapper, TestTree2> {

	public TestTree2 get(String id) {
		return super.get(id);
	}
	
	public List<TestTree2> findList(TestTree2 testTree2) {
		if (StringUtils.isNotBlank(testTree2.getParentIds())){
			testTree2.setParentIds(","+testTree2.getParentIds()+",");
		}
		return super.findList(testTree2);
	}
	
	@Transactional(readOnly = false)
	public void save(TestTree2 testTree2) {
		super.save(testTree2);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestTree2 testTree2) {
		super.delete(testTree2);
	}
	
}