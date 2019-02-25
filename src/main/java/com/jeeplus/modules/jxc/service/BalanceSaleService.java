/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.jxc.entity.Balance;
import com.jeeplus.modules.jxc.entity.BalanceSale;
import com.jeeplus.modules.jxc.mapper.BalanceSaleMapper;
import com.jeeplus.modules.jxc.entity.BalanceSaleDetail;
import com.jeeplus.modules.jxc.entity.OperOrder;
import com.jeeplus.modules.jxc.entity.OperOrderDetail;
import com.jeeplus.modules.jxc.entity.Price;
import com.jeeplus.modules.jxc.entity.Product;
import com.jeeplus.modules.jxc.mapper.BalanceSaleDetailMapper;

/**
 * 电子秤销售Service
 * @author FxLsoft
 * @version 2019-02-19
 */
@Service
@Transactional(readOnly = true)
public class BalanceSaleService extends CrudService<BalanceSaleMapper, BalanceSale> {
	
	public Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private OperOrderService operOrderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BalanceService balanceService;
	
	@Autowired
	private BalanceSaleDetailMapper balanceSaleDetailMapper;
	
	@Autowired
	private BalanceSaleMapper balanceSaleMapper;
	
	public List<BalanceSale> getSaleBalanceList() {
		return balanceSaleMapper.getSaleBalanceList();
	}
	
	public BalanceSale get(String id) {
		BalanceSale balanceSale = super.get(id);
		balanceSale.setBalanceSaleDetailList(balanceSaleDetailMapper.findList(new BalanceSaleDetail(balanceSale)));
		return balanceSale;
	}
	
	public List<BalanceSale> findList(BalanceSale balanceSale) {
		return super.findList(balanceSale);
	}
	
	public Page<BalanceSale> findPage(Page<BalanceSale> page, BalanceSale balanceSale) {
		return super.findPage(page, balanceSale);
	}
	
	@Transactional(readOnly = false)
	public void save(BalanceSale balanceSale) {
		super.save(balanceSale);
		for (BalanceSaleDetail balanceSaleDetail : balanceSale.getBalanceSaleDetailList()){
			if (balanceSaleDetail.getId() == null){
				continue;
			}
			if (BalanceSaleDetail.DEL_FLAG_NORMAL.equals(balanceSaleDetail.getDelFlag())){
				if (StringUtils.isBlank(balanceSaleDetail.getId())){
					balanceSaleDetail.setBalanceSale(balanceSale);
					balanceSaleDetail.preInsert();
					balanceSaleDetailMapper.insert(balanceSaleDetail);
				}else{
					balanceSaleDetail.preUpdate();
					balanceSaleDetailMapper.update(balanceSaleDetail);
				}
			}else{
				balanceSaleDetailMapper.delete(balanceSaleDetail);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void saveOnly(BalanceSale balanceSale) {
		super.save(balanceSale);
	}
	
	@Transactional(readOnly = false)
	public void delete(BalanceSale balanceSale) {
		super.delete(balanceSale);
		balanceSaleDetailMapper.delete(new BalanceSaleDetail(balanceSale));
	}
	
	@Transactional(readOnly = false)
	public ArrayList<String> dealBalanceSale (BalanceSale balanceSale) {
		ArrayList<String> errMsg = Lists.newArrayList();
		// 通过电子秤编号获取电子秤详情
		Balance balance = balanceService.findUniqueByProperty("no", balanceSale.getSaleId());
		OperOrder operOrder = new OperOrder();
		if (balance == null) {
			errMsg.add("电子秤编号：" + balanceSale.getSaleId() + " 未被管理；");
			logger.debug("电子秤编号：" + balanceSale.getSaleId() + " 未被管理；");
			
		} else {
			operOrder.setNo(balanceSale.getSaleNo());
			// 出库
			operOrder.setType("1");
			// 提交
			operOrder.setStatus("3");
			// 电子秤零售
			operOrder.setSource("3");
			operOrder.setStore(balance.getStore());
			operOrder.setTotalPrice(balanceSale.getRealPay());
			operOrder.setRealPay(balanceSale.getRealPay());
			operOrder.setRealPrice(balanceSale.getRealPay());
			operOrder.setOperOrderDetailList(Lists.newArrayList());
		}
		for (BalanceSaleDetail balanceSaleDetail: balanceSale.getBalanceSaleDetailList()) {
			// 通过产品计重编号获取产品详情
			Product product = productService.findUniqueByProperty("weight_no", balanceSaleDetail.getProductNo());
			if (product == null) {
				errMsg.add("计重编号为：" + balanceSaleDetail.getProductNo() + " 的产品未被管理；\r\n");
				logger.debug("计重编号为：" + balanceSaleDetail.getProductNo() + " 的产品未被管理；\r\n");
			} else if (balance != null){
				Price basePrice = null;
				product = productService.get(product.getId());
				for (Price price: product.getPriceList()) {
					logger.debug("产品规格与称的信息->" + price.getUnit() + "|" + balance.getBaseUnit());
					if (price.getUnit().trim().equals(balance.getBaseUnit())) {
						basePrice = price;
						break;
					}
				}
				if (basePrice == null) {
					errMsg.add(product.getName() + " 产品的价格设置与称的设置不相符；\r\n");
					logger.debug(product.getName() + " 产品的价格设置与称的设置不相符；\r\n");
				} else {
					OperOrderDetail operOrderDetail = new OperOrderDetail();
					operOrderDetail.setId("");
					operOrderDetail.setOperType("-1");
					operOrderDetail.setProduct(product);
					operOrderDetail.setPrice(basePrice);
					operOrderDetail.setAmount(balanceSaleDetail.getAmount());
					operOrderDetail.setOperPrice(balanceSaleDetail.getSalePrice());
					operOrder.getOperOrderDetailList().add(operOrderDetail);
				}
			}
		}
		if (errMsg.size() == 0) {
			// 操作库存 先检查再入库
			List<Product> errDetails = operOrderService.dealStorage(operOrder, true);
			if (errDetails.size() == 0) {
				operOrderService.dealStorage(operOrder, false);
				operOrderService.save(operOrder);
				logger.debug("创建电子秤销售单据成功-> " + operOrder.getNo());
			} else {
				StringBuffer msg = new StringBuffer();
				for (Product product: errDetails) {
					msg.append(product.getRemarks() + "\r\n");
				}
				errMsg.add(msg.toString());
			}
		}
		return errMsg;
	}
}