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
import com.jeeplus.modules.jxc.entity.Warehouse;
import com.jeeplus.modules.jxc.mapper.WarehouseMapper;

/**
 * 商品仓库Service
 * @author 周涛
 * @version 2018-12-25
 */
@Service
@Transactional(readOnly = true)
public class WarehouseService extends CrudService<WarehouseMapper, Warehouse> {
	
	@Autowired
	private WarehouseMapper warehouseMapper;

	public Warehouse get(String id) {
		return super.get(id);
	}
	
	public List<Warehouse> findList(Warehouse warehouse) {
		return super.findList(warehouse);
	}
	
	public Page<Warehouse> findPage(Page<Warehouse> page, Warehouse warehouse) {
		return super.findPage(page, warehouse);
	}
	
	@Transactional(readOnly = false)
	public void save(Warehouse warehouse) {
		super.save(warehouse);
	}
	
	@Transactional(readOnly = false)
	public void delete(Warehouse warehouse) {
		super.delete(warehouse);
	}
	
	public Double sumProduct(String productId, Integer isRetail, String company) {
		return warehouseMapper.sumProduct(productId, isRetail, company);
	}
	
	public List<Warehouse> selectWarehouseById(String productId, Integer isRetail, String company) {
		return warehouseMapper.selectWarehouseById(productId, isRetail, company);
	}
	
	@Transactional(readOnly = false)
	public int deleteByLogic(Warehouse warehouse) {
		return warehouseMapper.deleteByLogic(warehouse);
	}
}