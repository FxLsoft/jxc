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
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.jxc.entity.Product;
import com.jeeplus.modules.jxc.mapper.ProductMapper;
import com.jeeplus.modules.jxc.entity.Price;
import com.jeeplus.modules.jxc.mapper.PriceMapper;

/**
 * 商品Service
 * @author FxLsoft
 * @version 2019-02-11
 */
@Service
@Transactional(readOnly = true)
public class ProductService extends CrudService<ProductMapper, Product> {

	@Autowired
	private PriceMapper priceMapper;
	
	@Autowired
	private ProductMapper productMapper;
	
	public Product get(String id) {
		Product product = super.get(id);
		product.setPriceList(priceMapper.findList(new Price(product)));
		return product;
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
		for (Price price : product.getPriceList()){
			if (price.getId() == null){
				continue;
			}
			if (Price.DEL_FLAG_NORMAL.equals(price.getDelFlag())){
				if (StringUtils.isBlank(price.getId())){
					price.setProduct(product);
					price.preInsert();
					priceMapper.insert(price);
				}else{
					price.preUpdate();
					priceMapper.update(price);
				}
			}else{
				priceMapper.delete(price);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Product product) {
		super.delete(product);
		priceMapper.delete(new Price(product));
	}
	
	public Product getProductByWeightNo(String weightNo, String storeId) {
		return productMapper.getProductByWeightNo(weightNo, storeId);
	}
}