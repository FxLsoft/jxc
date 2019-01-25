/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.treetable.form.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.test.treetable.form.entity.Car2;
import com.jeeplus.modules.test.treetable.form.mapper.Car2Mapper;

/**
 * 车辆Service
 * @author lgf
 * @version 2018-06-12
 */
@Service
@Transactional(readOnly = true)
public class Car2Service extends CrudService<Car2Mapper, Car2> {

	public Car2 get(String id) {
		return super.get(id);
	}
	
	public List<Car2> findList(Car2 car2) {
		return super.findList(car2);
	}
	
	public Page<Car2> findPage(Page<Car2> page, Car2 car2) {
		return super.findPage(page, car2);
	}
	
	@Transactional(readOnly = false)
	public void save(Car2 car2) {
		super.save(car2);
	}
	
	@Transactional(readOnly = false)
	public void delete(Car2 car2) {
		super.delete(car2);
	}
	
}