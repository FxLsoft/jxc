/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jxc.entity.Store;
import com.jeeplus.modules.jxc.mapper.StoreMapper;

/**
 * 门店Service
 * @author FxLsoft
 * @version 2019-02-11
 */
@Service
@Transactional(readOnly = true)
public class StoreService extends CrudService<StoreMapper, Store> {
	
	@Autowired
	private StoreMapper storeMapper;

	public Store get(String id) {
		return super.get(id);
	}
	
	public List<Store> findList(Store store) {
		return super.findList(store);
	}
	
	public Page<Store> findPage(Page<Store> page, Store store) {
		return super.findPage(page, store);
	}
	
	@Transactional(readOnly = false)
	public void save(Store store) {
		super.save(store);
	}
	
	@Transactional(readOnly = false)
	public void delete(Store store) {
		super.delete(store);
	}
	
	public List<Store> getAllStore() {
		return storeMapper.getAllStore();
	}
}