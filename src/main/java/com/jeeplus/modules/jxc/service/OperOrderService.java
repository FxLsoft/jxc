/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.base.ObjectUtil;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jxc.entity.OperOrder;
import com.jeeplus.modules.jxc.entity.OperOrderDetail;
import com.jeeplus.modules.jxc.entity.OperOrderPay;
import com.jeeplus.modules.jxc.entity.Price;
import com.jeeplus.modules.jxc.entity.Product;
import com.jeeplus.modules.jxc.entity.Storage;
import com.jeeplus.modules.jxc.mapper.OperOrderDetailMapper;
import com.jeeplus.modules.jxc.mapper.OperOrderMapper;
import com.jeeplus.modules.jxc.mapper.OperOrderPayMapper;

/**
 * 单据Service
 * @author FxLsoft
 * @version 2019-02-13
 */
@Service
@Transactional(readOnly = true)
public class OperOrderService extends CrudService<OperOrderMapper, OperOrder> {
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private OperOrderDetailMapper operOrderDetailMapper;
	@Autowired
	private OperOrderPayMapper operOrderPayMapper;
	@Autowired
	private OperOrderMapper operOrderMapper;
	
	public OperOrder get(String id) {
		OperOrder operOrder = super.get(id);
		operOrder.setOperOrderDetailList(operOrderDetailMapper.findList(new OperOrderDetail(operOrder)));
		operOrder.setOperOrderPayList(operOrderPayMapper.findList(new OperOrderPay(operOrder)));
		return operOrder;
	}
	
	public List<OperOrder> findList(OperOrder operOrder) {
		return super.findList(operOrder);
	}
	
	public Page<OperOrder> findPage(Page<OperOrder> page, OperOrder operOrder) {
		return super.findPage(page, operOrder);
	}
	
	public List<OperOrder> findListByWhere(String storeId, Date startDate, Date endDate) {
		return operOrderMapper.findListByWhere(storeId, startDate, endDate);
	}
	
	@Transactional(readOnly = false)
	public void save(OperOrder operOrder) {
		super.save(operOrder);
		for (OperOrderDetail operOrderDetail : operOrder.getOperOrderDetailList()){
			if (operOrderDetail.getId() == null){
				continue;
			}
			if (OperOrderDetail.DEL_FLAG_NORMAL.equals(operOrderDetail.getDelFlag())){
				if (StringUtils.isBlank(operOrderDetail.getId())){
					operOrderDetail.setOperOrder(operOrder);
					operOrderDetail.preInsert();
					operOrderDetailMapper.insert(operOrderDetail);
				}else{
					operOrderDetail.preUpdate();
					operOrderDetailMapper.update(operOrderDetail);
				}
			}else{
				operOrderDetailMapper.delete(operOrderDetail);
			}
		}
		for (OperOrderPay operOrderPay : operOrder.getOperOrderPayList()){
			if (operOrderPay.getId() == null){
				continue;
			}
			if (OperOrderPay.DEL_FLAG_NORMAL.equals(operOrderPay.getDelFlag())){
				if (StringUtils.isBlank(operOrderPay.getId())){
					operOrderPay.setOperOrder(operOrder);
					operOrderPay.preInsert();
					operOrderPayMapper.insert(operOrderPay);
				}else{
					operOrderPay.preUpdate();
					operOrderPayMapper.update(operOrderPay);
				}
			}else{
				operOrderPayMapper.delete(operOrderPay);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void saveOnly(OperOrder operOrder) {
		super.save(operOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(OperOrder operOrder) {
		super.delete(operOrder);
		operOrderDetailMapper.delete(new OperOrderDetail(operOrder));
		operOrderPayMapper.delete(new OperOrderPay(operOrder));
	}
	
	@Transactional(readOnly = false)
	public int updateOrderStatus(String id, String status) {
		return mapper.updateOrderStatus(id, status);
	}
	
	@Transactional(readOnly = false)
	public List<Product> dealStorage(OperOrder operOrder, Boolean isCheck) {
		logger.debug("开始操作库存..." + operOrder.getNo());
		// 错误具体信息
		List<Product> errDetail = Lists.newArrayList();
		// 如果是检查或者是入库的话直接返回
		if (isCheck && "0".equals(operOrder.getType())) {
			return errDetail;
		}
		// 详情
		List<OperOrderDetail> detailList = operOrder.getOperOrderDetailList();
		// 同一个产品聚合
		List<Product> operProductList = Lists.newArrayList();
		// 
		for (int i = 0, size = detailList.size(); i < size; i++) {
			OperOrderDetail operOrderDetail = detailList.get(i);
			Double operType = Double.parseDouble(operOrderDetail.getOperType());
			Product product = null;
			for (int j = 0, jSize = operProductList.size(); j <= jSize; j++) {
				// 如果没有Product
				if (j == jSize) {
					// 获取库存详情
					Storage sStorage = new Storage();
					sStorage.setProduct(operOrderDetail.getProduct());
					sStorage.setStore(operOrder.getStore());
					product = productService.get(operOrderDetail.getProduct().getId());
					// 获取产品库存情况
					logger.debug("查询库存->" + product.getName());
					List<Storage> storageList = storageService.getStorageList(sStorage);
					List<Storage> storageL = Lists.newArrayList();
					for (Storage storage: storageList) {
						Storage s = new Storage();
						s.setAmount(storage.getAmount());
						s.setStore(storage.getStore());
						s.setPrice(storage.getPrice());
						s.setId(storage.getId());
						s.setProduct(storage.getProduct());
						storageL.add(s);
					}
					logger.debug("库存" + ObjectUtil.toPrettyString(storageList));
					// 默认为 0
					product.setAmount(0d);
					product.setStorageAmount(0d);
					product.setStorageList(storageL);
					for (Price price: product.getPriceList()) {
						price.setAmount(0d);
						price.setStorageAmount(0d);
					}
					operProductList.add(product);
					
				} else if (operProductList.get(j).getId().equals(operOrderDetail.getProduct().getId())) {
					product = operProductList.get(j);
					break;
				}
			}
			for (int j = product.getPriceList().size() - 1; j >= 0; j--) {
				Price price = product.getPriceList().get(j);
				if (price.getAmount() == null) {
					price.setAmount(0d);
				}
				if (price.getStorageAmount() == null) {
					price.setStorageAmount(0d);
				}
				if (price.getId().equals(operOrderDetail.getPrice().getId())) {
					logger.debug("设置数量-> " + operType + " | " + product.getName() + " | " + price.getUnit() + " | " + operOrderDetail.getAmount());
					price.setAmount(price.getAmount() + operOrderDetail.getAmount() * operType * price.getRatio());
					product.setAmount(price.getAmount() + product.getAmount());
					// 结算
					price.setRemindAmount(price.getAmount() + price.getStorageAmount());
					
				}
				for (Storage storage: product.getStorageList()) {
					if (storage.getPrice().getId().equals(price.getId())) {
						logger.debug("设置库存-> " + product.getName() + " | " + price.getUnit() + " | " + storage.getAmount());
						price.setStorageAmount(storage.getAmount() * price.getRatio());
						product.setStorageAmount(product.getStorageAmount() + price.getStorageAmount());
						// 结算
						price.setRemindAmount(price.getAmount() + price.getStorageAmount());
						storage.setAmount(0d);
					}
				}
				// 如果是基本单位
				if (product.getBaseUnit() == null && ("1".equals(price.getIsBasic()) || j == 0)) {
					product.setBaseUnit(j);
				}
			}
		}
		logger.debug("库存情况：" + operProductList.toString());
		
		for (Product operProduct: operProductList) {
			Double remaindermount = operProduct.getAmount() + operProduct.getStorageAmount();
			logger.debug("操作情况：" + operProduct.getAmount() + " | " + operProduct.getStorageAmount() + " | " + remaindermount);
			if (isCheck) {
				if (remaindermount < 0) {
					operProduct.setRemarks(operProduct.getName() + " 产品库存不足！");
					errDetail.add(operProduct);
				}
			} else {
				storageService.deleteByProductId(operProduct.getId());
				logger.debug("删除 " + operProduct.getName() + " 库存");
				for (int i = operProduct.getPriceList().size() -1; i >= 0; i--) {
					Price price = operProduct.getPriceList().get(i);
					Double amount = Math.floor(remaindermount / price.getRatio());
					remaindermount = remaindermount - amount*price.getRatio();
					if (remaindermount > 0 && i == 0) {
						amount += remaindermount/price.getRatio();
					}
					Storage storage = new Storage();
					storage.setStore(operOrder.getStore());
					storage.setProduct(operProduct);
					storage.setPrice(price);
					if (amount > 0) {
						logger.debug("添加库存 " + operProduct.getName()  + " " + price.getUnit() + " " + amount);
						storage.setAmount(amount);
						storageService.save(storage);
					}
				}
			}
		}
		
//		for (Product operProduct: operProductList) {
//			Price sPrice = new Price();
//			sPrice.setProduct(operProduct);
//			// 获取产品价格属性表 按照换算比例升序
//			List<Price> priceList = priceService.findList(sPrice);
//			Storage sStorage = new Storage();
//			sStorage.setProduct(operProduct);
//			sStorage.setStore(operOrder.getStore());
//			// 获取产品库存情况
//			List<Storage> storageList = storageService.findList(sStorage);
//			// 标识基本价格
//			int base = -1;
//			// 入库产品价格信息
//			int orderIndex = -1;
//			for (int i = 0; i< priceList.size(); i++) {
//				for (int j = 0; j < storageList.size(); j++) {
//					if (priceList.get(i).getId().equals(storageList.get(j).getPrice().getId())) {
//						priceList.get(i).setStorage(storageList.get(j));
//						storageList.remove(j);
//						break;
//					}
//				}
//				if ("0".equals(priceList.get(i).getIsBasic())) {
//					base = i;
//				} else if (i == priceList.size() - 1) {
//					base = 0;
//				}
//				if (priceList.get(i).getId().equals(operOrderDetail.getPrice().getId())) {
//					orderIndex = i;
//				}
//			}
//			if (base == -1 || orderIndex == -1) {
//				// 库存操作失败 原因：产品价格信息错误
//				operOrderDetail.setRemarks("库存操作失败 原因： " + operOrderDetail.getProduct().getName() + " 产品价格信息错误");
//				errDetail.add(operOrderDetail);
//				continue;
//			}
//			// 减库
//			if ("-1".equals(operOrderDetail.getOperType())) {
//				// 减
//				Double storageAmount = 0d;
//				Double needAmount = priceList.get(orderIndex).getRatio() / priceList.get(base).getRatio() * operOrderDetail.getAmount();
//				for (int i = 0; i< priceList.size(); i++) {
//					if (priceList.get(i).getStorage() != null) {
//						storageAmount += priceList.get(i).getRatio() / priceList.get(base).getRatio() * priceList.get(i).getStorage().getAmount();
//					}
//				}
//				// 剩余库存
//				Double remainder = storageAmount - needAmount;
//				// 库存不足
//				if (remainder < 0) {
//					// 库存操作失败 原因：产品价格信息错误
//					operOrderDetail.setRemarks("库存操作失败 原因： " + operOrderDetail.getProduct().getName() + " 产品价格信息错误");
//					errDetail.add(operOrderDetail);
//					continue;
//				} else {
//					if (isCheck) continue;
//					for (int i = priceList.size() - 1; i >= 0; i--) {
//						if (base == i) {
//							Storage storage = priceList.get(orderIndex).getStorage();
//							if (storage == null) {
//								storage = new Storage();
//								storage.setAmount(remainder);
//								storage.setPrice(operOrderDetail.getPrice());
//								storage.setProduct(operOrderDetail.getProduct());
//								storage.setStore(operOrder.getStore());
//							} else {
//								storage.setAmount(remainder);
//							}
//							remainder = 0d;
//							storageService.save(storage);
//						}
//						if (priceList.get(i).getStorage() != null && priceList.get(i).getStorage().getAmount() > 0) {
//							Double amount = Math.floor(priceList.get(base).getRatio() / priceList.get(i).getRatio() * remainder);
//							if (amount >= priceList.get(i).getStorage().getAmount() ) {
//								remainder = remainder - priceList.get(i).getStorage().getAmount() * priceList.get(i).getRatio() / priceList.get(base).getRatio();
//							} else {
//								remainder = remainder - amount * priceList.get(i).getRatio() / priceList.get(base).getRatio();
//								priceList.get(i).getStorage().setAmount(amount);
//								storageService.save(priceList.get(i).getStorage());
//							}
//						}
//					}
//				}
//			}
//			// 加库
//			else {
//				if (isCheck) continue;
//				// 如果没有相应的产品库存，那么就创建一条记录
//				Storage storage = priceList.get(orderIndex).getStorage();
//				if (storage == null) {
//					storage = new Storage();
//					storage.setAmount(operOrderDetail.getAmount());
//					storage.setPrice(operOrderDetail.getPrice());
//					storage.setProduct(operOrderDetail.getProduct());
//					storage.setStore(operOrder.getStore());
//				} else {
//					storage.setAmount(operOrderDetail.getAmount() + storage.getAmount());
//				}
//				// 保存 库存
//				storageService.save(storage);
//				logger.debug("加库：" + storage.toString());
//			}
//		}
		return errDetail;
	}
}