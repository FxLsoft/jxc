/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.treetable.form.web;

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
import com.jeeplus.modules.test.treetable.form.entity.CarKind2;
import com.jeeplus.modules.test.treetable.form.service.CarKind2Service;

/**
 * 车系Controller
 * @author lgf
 * @version 2018-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/test/treetable/form/carKind2")
public class CarKind2Controller extends BaseController {

	@Autowired
	private CarKind2Service carKind2Service;
	
	@ModelAttribute
	public CarKind2 get(@RequestParam(required=false) String id) {
		CarKind2 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = carKind2Service.get(id);
		}
		if (entity == null){
			entity = new CarKind2();
		}
		return entity;
	}
	
	/**
	 * 车系列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(CarKind2 carKind2,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/test/treetable/form/carKind2List";
	}

	/**
	 * 查看，增加，编辑车系表单页面
	 */
	@RequestMapping(value = "form")
	public String form(CarKind2 carKind2, Model model) {
		if (carKind2.getParent()!=null && StringUtils.isNotBlank(carKind2.getParent().getId())){
			carKind2.setParent(carKind2Service.get(carKind2.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(carKind2.getId())){
				CarKind2 carKind2Child = new CarKind2();
				carKind2Child.setParent(new CarKind2(carKind2.getParent().getId()));
				List<CarKind2> list = carKind2Service.findList(carKind2); 
				if (list.size() > 0){
					carKind2.setSort(list.get(list.size()-1).getSort());
					if (carKind2.getSort() != null){
						carKind2.setSort(carKind2.getSort() + 30);
					}
				}
			}
		}
		if (carKind2.getSort() == null){
			carKind2.setSort(30);
		}
		model.addAttribute("carKind2", carKind2);
		return "modules/test/treetable/form/carKind2Form";
	}

	/**
	 * 保存车系
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(CarKind2 carKind2, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(carKind2);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		carKind2Service.save(carKind2);//保存
		j.setSuccess(true);
		j.put("carKind2", carKind2);
		j.setMsg("保存车系成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<CarKind2> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return carKind2Service.getChildren(parentId);
	}
	
	/**
	 * 删除车系
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(CarKind2 carKind2) {
		AjaxJson j = new AjaxJson();
		carKind2Service.delete(carKind2);
		j.setSuccess(true);
		j.setMsg("删除车系成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<CarKind2> list = carKind2Service.findList(new CarKind2());
		for (int i=0; i<list.size(); i++){
			CarKind2 e = list.get(i);
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