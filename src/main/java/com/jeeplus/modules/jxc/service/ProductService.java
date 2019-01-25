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
import com.jeeplus.modules.jxc.entity.Product;
import com.jeeplus.modules.jxc.mapper.ProductMapper;

/**
 * 商品信息Service
 * @author FxLsoft
 * @version 2018-12-22
 */
@Service
@Transactional(readOnly = true)
public class ProductService extends CrudService<ProductMapper, Product> {
	
	@Autowired
	private ProductMapper productMapper;

	public Product get(String id) {
		return super.get(id);
	}
	
	public List<Product> findList(Product product) {
		return super.findList(product);
	}
	
	public Page<Product> findPage(Page<Product> page, Product product) {
		return super.findPage(page, product);
	}
	
	@Transactional(readOnly = false)
	public void save(Product product) {
		super.save(product);
	}
	
	@Transactional(readOnly = false)
	public void delete(Product product) {
		super.delete(product);
	}
	
	public List<Product> queryProductByKey(String key, Integer retail, int start, int end, String company) {
		return productMapper.queryProductByKey(key, retail, start, end, company);
	}
	
	public Integer countProductByKey(String value, Integer retail, String company) {
		return productMapper.countProductByKey(value, retail, company);
	}
}