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
import com.jeeplus.modules.jxc.entity.Storage;
import com.jeeplus.modules.jxc.mapper.StorageMapper;

/**
 * 库存Service
 * @author FxLsoft
 * @version 2019-02-12
 */
@Service
@Transactional(readOnly = true)
public class StorageService extends CrudService<StorageMapper, Storage> {
	
	@Autowired
	private StorageMapper storageMapper;

	public Storage get(String id) {
		return super.get(id);
	}
	
	public List<Storage> findList(Storage storage) {
		return super.findList(storage);
	}
	
	public Page<Storage> findPage(Page<Storage> page, Storage storage) {
		return super.findPage(page, storage);
	}
	
	public List<Storage> getStorageList(Storage storage) {
		return storageMapper.getStorageList(storage);
	}
	
	@Transactional(readOnly = false)
	public void save(Storage storage) {
		super.save(storage);
	}
	
	@Transactional(readOnly = false)
	public void delete(Storage storage) {
		super.delete(storage);
	}
	
	@Transactional(readOnly = false)
	public int deleteByProductId(String productId,String storeId) {
		return storageMapper.deleteByProductId(productId, storeId);
	}
}