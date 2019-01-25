/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.treetable.dialog.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.config.Global;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.test.treetable.dialog.entity.CarKind1;
import com.jeeplus.modules.test.treetable.dialog.service.CarKind1Service;

/**
 * 车系Controller
 * @author lgf
 * @version 2018-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/test/treetable/dialog/carKind1")
public class CarKind1Controller extends BaseController {

	@Autowired
	private CarKind1Service carKind1Service;
	
	@ModelAttribute
	public CarKind1 get(@RequestParam(required=false) String id) {
		CarKind1 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = carKind1Service.get(id);
		}
		if (entity == null){
			entity = new CarKind1();
		}
		return entity;
	}
	
	/**
	 * 车系列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(CarKind1 carKind1,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/test/treetable/dialog/carKind1List";
	}

	/**
	 * 查看，增加，编辑车系表单页面
	 */
	@RequestMapping(value = "form")
	public String form(CarKind1 carKind1, Model model) {
		if (carKind1.getParent()!=null && StringUtils.isNotBlank(carKind1.getParent().getId())){
			carKind1.setParent(carKind1Service.get(carKind1.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(carKind1.getId())){
				CarKind1 carKind1Child = new CarKind1();
				carKind1Child.setParent(new CarKind1(carKind1.getParent().getId()));
				List<CarKind1> list = carKind1Service.findList(carKind1); 
				if (list.size() > 0){
					carKind1.setSort(list.get(list.size()-1).getSort());
					if (carKind1.getSort() != null){
						carKind1.setSort(carKind1.getSort() + 30);
					}
				}
			}
		}
		if (carKind1.getSort() == null){
			carKind1.setSort(30);
		}
		model.addAttribute("carKind1", carKind1);
		return "modules/test/treetable/dialog/carKind1Form";
	}

	/**
	 * 保存车系
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(CarKind1 carKind1, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(carKind1);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		carKind1Service.save(carKind1);//保存
		j.setSuccess(true);
		j.put("carKind1", carKind1);
		j.setMsg("保存车系成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<CarKind1> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return carKind1Service.getChildren(parentId);
	}
	
	/**
	 * 删除车系
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(CarKind1 carKind1) {
		AjaxJson j = new AjaxJson();
		carKind1Service.delete(carKind1);
		j.setSuccess(true);
		j.setMsg("删除车系成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<CarKind1> list = carKind1Service.findList(new CarKind1());
		for (int i=0; i<list.size(); i++){
			CarKind1 e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("text", e.getName());
				if(StringUtils.isBlank(e.getParentId()) || "0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}