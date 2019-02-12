package com.jeeplus.modules.jxc.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.jxc.entity.Price;
import com.jeeplus.modules.jxc.service.PriceService;

@Controller
@RequestMapping(value = "${adminPath}/jxc/price")
public class PriceController extends BaseController{

	@Autowired
	private PriceService priceService;
	
	/**
	 * 商品列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:product:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Price price, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Price> page = priceService.findPage(new Page<Price>(request, response), price); 
		return getBootstrapData(page);
	}
}
