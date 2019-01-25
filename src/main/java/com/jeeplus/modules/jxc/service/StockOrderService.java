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
import com.jeeplus.modules.jxc.entity.StockOrder;
import com.jeeplus.modules.jxc.mapper.StockOrderMapper;
import com.jeeplus.modules.jxc.entity.StockOrderDetail;
import com.jeeplus.modules.jxc.mapper.StockOrderDetailMapper;

/**
 * 入库单Service
 * @author 周涛
 * @version 2018-12-25
 */
@Service
@Transactional(readOnly = true)
public class StockOrderService extends CrudService<StockOrderMapper, StockOrder> {

	@Autowired
	private StockOrderDetailMapper stockOrderDetailMapper;
	
	public StockOrder get(String id) {
		StockOrder stockOrder = super.get(id);
		stockOrder.setStockOrderDetailList(stockOrderDetailMapper.findList(new StockOrderDetail(stockOrder)));
		return stockOrder;
	}
	
	public List<StockOrder> findList(StockOrder stockOrder) {
		return super.findList(stockOrder);
	}
	
	public Page<StockOrder> findPage(Page<StockOrder> page, StockOrder stockOrder) {
		return super.findPage(page, stockOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(StockOrder stockOrder) {
		super.save(stockOrder);
		for (StockOrderDetail stockOrderDetail : stockOrder.getStockOrderDetailList()){
			if (stockOrderDetail.getId() == null){
				continue;
			}
			if (StockOrderDetail.DEL_FLAG_NORMAL.equals(stockOrderDetail.getDelFlag())){
				if (StringUtils.isBlank(stockOrderDetail.getId())){
					stockOrderDetail.setStockOrder(stockOrder);
					stockOrderDetail.preInsert();
					stockOrderDetailMapper.insert(stockOrderDetail);
				}else{
					stockOrderDetail.preUpdate();
					stockOrderDetailMapper.update(stockOrderDetail);
				}
			}else{
				stockOrderDetailMapper.delete(stockOrderDetail);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(StockOrder stockOrder) {
		super.delete(stockOrder);
		stockOrderDetailMapper.delete(new StockOrderDetail(stockOrder));
	}
	
}