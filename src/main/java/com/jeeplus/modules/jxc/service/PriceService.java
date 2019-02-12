package com.jeeplus.modules.jxc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jxc.entity.Price;
import com.jeeplus.modules.jxc.mapper.PriceMapper;

@Service
@Transactional(readOnly = true)
public class PriceService extends CrudService<PriceMapper, Price> {
	
	public List<Price> findList(Price price) {
		return super.findAllList(price);
	}
	
	public Page<Price> findPage(Page<Price> page, Price price) {
		return super.findPage(page, price);
	}
}
