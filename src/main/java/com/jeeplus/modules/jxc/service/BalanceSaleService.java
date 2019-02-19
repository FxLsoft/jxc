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
import com.jeeplus.modules.jxc.entity.BalanceSale;
import com.jeeplus.modules.jxc.mapper.BalanceSaleMapper;
import com.jeeplus.modules.jxc.entity.BalanceSaleDetail;
import com.jeeplus.modules.jxc.mapper.BalanceSaleDetailMapper;

/**
 * 电子秤销售Service
 * @author FxLsoft
 * @version 2019-02-19
 */
@Service
@Transactional(readOnly = true)
public class BalanceSaleService extends CrudService<BalanceSaleMapper, BalanceSale> {

	@Autowired
	private BalanceSaleDetailMapper balanceSaleDetailMapper;
	
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
			balanceSaleDetail.setBalanceSale(balanceSale);
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
	
}