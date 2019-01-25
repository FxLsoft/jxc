/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.treetable.form.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.test.treetable.form.entity.CarKind2;
import com.jeeplus.modules.test.treetable.form.mapper.CarKind2Mapper;

/**
 * 车系Service
 * @author lgf
 * @version 2018-06-12
 */
@Service
@Transactional(readOnly = true)
public class CarKind2Service extends TreeService<CarKind2Mapper, CarKind2> {

	public CarKind2 get(String id) {
		return super.get(id);
	}
	
	public List<CarKind2> findList(CarKind2 carKind2) {
		if (StringUtils.isNotBlank(carKind2.getParentIds())){
			carKind2.setParentIds(","+carKind2.getParentIds()+",");
		}
		return super.findList(carKind2);
	}
	
	@Transactional(readOnly = false)
	public void save(CarKind2 carKind2) {
		super.save(carKind2);
	}
	
	@Transactional(readOnly = false)
	public void delete(CarKind2 carKind2) {
		super.delete(carKind2);
	}
	
}