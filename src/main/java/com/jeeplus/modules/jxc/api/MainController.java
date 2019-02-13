package com.jeeplus.modules.jxc.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.jxc.entity.Price;
import com.jeeplus.modules.jxc.service.PriceService;

@Controller
@RequestMapping(value = "${adminPath}/api")
public class MainController extends BaseController{
	
	@Autowired
	private PriceService priceService;
	
	/**
	 * 获取 Price 列表
	 * @param price
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getPrice")
	@ResponseBody
	public Map<String, Object> getPrice(Price price, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Price> page = priceService.findPage(new Page<Price>(request, response), price);
		return getBootstrapData(page);
	}
	
	@RequestMapping("/uploadBalanceSell")
	@ResponseBody
	public AjaxJson uploadBalanceSell() {
		AjaxJson result = new AjaxJson();
		return result;
	}
	
	
	
}
