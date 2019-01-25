/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.onetomany.dialog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.test.onetomany.dialog.entity.TestDataMain1;
import com.jeeplus.modules.test.onetomany.dialog.mapper.TestDataMain1Mapper;
import com.jeeplus.modules.test.onetomany.dialog.entity.TestDataChild11;
import com.jeeplus.modules.test.onetomany.dialog.mapper.TestDataChild11Mapper;
import com.jeeplus.modules.test.onetomany.dialog.entity.TestDataChild12;
import com.jeeplus.modules.test.onetomany.dialog.mapper.TestDataChild12Mapper;
import com.jeeplus.modules.test.onetomany.dialog.entity.TestDataChild13;
import com.jeeplus.modules.test.onetomany.dialog.mapper.TestDataChild13Mapper;

/**
 * 票务代理Service
 * @author liugf
 * @version 2018-06-12
 */
@Service
@Transactional(readOnly = true)
public class TestDataMain1Service extends CrudService<TestDataMain1Mapper, TestDataMain1> {

	@Autowired
	private TestDataChild11Mapper testDataChild11Mapper;
	@Autowired
	private TestDataChild12Mapper testDataChild12Mapper;
	@Autowired
	private TestDataChild13Mapper testDataChild13Mapper;
	
	public TestDataMain1 get(String id) {
		TestDataMain1 testDataMain1 = super.get(id);
		testDataMain1.setTestDataChild11List(testDataChild11Mapper.findList(new TestDataChild11(testDataMain1)));
		testDataMain1.setTestDataChild12List(testDataChild12Mapper.findList(new TestDataChild12(testDataMain1)));
		testDataMain1.setTestDataChild13List(testDataChild13Mapper.findList(new TestDataChild13(testDataMain1)));
		return testDataMain1;
	}
	
	public List<TestDataMain1> findList(TestDataMain1 testDataMain1) {
		return super.findList(testDataMain1);
	}
	
	public Page<TestDataMain1> findPage(Page<TestDataMain1> page, TestDataMain1 testDataMain1) {
		return super.findPage(page, testDataMain1);
	}
	
	@Transactional(readOnly = false)
	public void save(TestDataMain1 testDataMain1) {
		super.save(testDataMain1);
		for (TestDataChild11 testDataChild11 : testDataMain1.getTestDataChild11List()){
			if (testDataChild11.getId() == null){
				continue;
			}
			if (TestDataChild11.DEL_FLAG_NORMAL.equals(testDataChild11.getDelFlag())){
				if (StringUtils.isBlank(testDataChild11.getId())){
					testDataChild11.setTestDataMain(testDataMain1);
					testDataChild11.preInsert();
					testDataChild11Mapper.insert(testDataChild11);
				}else{
					testDataChild11.preUpdate();
					testDataChild11Mapper.update(testDataChild11);
				}
			}else{
				testDataChild11Mapper.delete(testDataChild11);
			}
		}
		for (TestDataChild12 testDataChild12 : testDataMain1.getTestDataChild12List()){
			if (testDataChild12.getId() == null){
				continue;
			}
			if (TestDataChild12.DEL_FLAG_NORMAL.equals(testDataChild12.getDelFlag())){
				if (StringUtils.isBlank(testDataChild12.getId())){
					testDataChild12.setTestDataMain(testDataMain1);
					testDataChild12.preInsert();
					testDataChild12Mapper.insert(testDataChild12);
				}else{
					testDataChild12.preUpdate();
					testDataChild12Mapper.update(testDataChild12);
				}
			}else{
				testDataChild12Mapper.delete(testDataChild12);
			}
		}
		for (TestDataChild13 testDataChild13 : testDataMain1.getTestDataChild13List()){
			if (testDataChild13.getId() == null){
				continue;
			}
			if (TestDataChild13.DEL_FLAG_NORMAL.equals(testDataChild13.getDelFlag())){
				if (StringUtils.isBlank(testDataChild13.getId())){
					testDataChild13.setTestDataMain(testDataMain1);
					testDataChild13.preInsert();
					testDataChild13Mapper.insert(testDataChild13);
				}else{
					testDataChild13.preUpdate();
					testDataChild13Mapper.update(testDataChild13);
				}
			}else{
				testDataChild13Mapper.delete(testDataChild13);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TestDataMain1 testDataMain1) {
		super.delete(testDataMain1);
		testDataChild11Mapper.delete(new TestDataChild11(testDataMain1));
		testDataChild12Mapper.delete(new TestDataChild12(testDataMain1));
		testDataChild13Mapper.delete(new TestDataChild13(testDataMain1));
	}
	
}