/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.act.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeeplus.common.json.AjaxJson;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.act.service.ActModelService;

import java.util.Map;

/**
 * 流程模型相关Controller
 * @author jeeplus
 * @version 2016-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/act/model")
public class ActModelController extends BaseController {

	@Autowired
	private ActModelService actModelService;

	/**
	 * 流程模型列表
	 */
	@ResponseBody
	@RequiresPermissions("act:model:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(String category, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<org.activiti.engine.repository.Model> page = actModelService.modelList(
				new Page<org.activiti.engine.repository.Model>(request, response), category);
		return getBootstrapData(page);
	}


	/**
	 * 流程模型列表
	 */
	@RequiresPermissions("act:model:list")
	@RequestMapping(value = { "list", "" })
	public String modelList(String category, HttpServletRequest request, HttpServletResponse response, Model model) {

		return "modules/bpm/model/modelList";
	}

	/**
	 * 创建模型
	 */
	@RequiresPermissions("act:model:create")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		return "modules/bpm/model/modelCreate";
	}
	
	/**
	 * 创建模型
	 */
	@ResponseBody
	@RequiresPermissions("act:model:create")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public AjaxJson create(String name, String key, String description, String category,
			HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			actModelService.create(name, key, description, category);
			j.setMsg("添加模型成功");

		} catch (Exception e) {
			j.setMsg("添加模型失败");
			logger.error("创建模型失败：", e);
		}
		return j;
	}

	/**
	 * 根据Model部署流程
	 */
	@ResponseBody
	@RequiresPermissions("act:model:deploy")
	@RequestMapping(value = "deploy")
	public AjaxJson deploy(String id) {
		AjaxJson j = new AjaxJson();
		String message = actModelService.deploy(id);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 导出model的xml文件
	 */
	@RequiresPermissions("act:model:export")
	@RequestMapping(value = "export")
	public void export(String id, HttpServletResponse response) {
		actModelService.export(id, response);
	}

	/**
	 * 更新Model分类
	 */
	@ResponseBody
	@RequiresPermissions("act:model:edit")
	@RequestMapping(value = "updateCategory")
	public AjaxJson updateCategory(String id, String category) {
		AjaxJson j = new AjaxJson();
		actModelService.updateCategory(id, category);
		j.setMsg("设置成功，模块ID=" + id);
		return j;
	}
	
	/**
	 * 删除Model
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("act:model:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String id) {
		AjaxJson j = new AjaxJson();
		actModelService.delete(id);
		j.setMsg("删除成功，模型ID=" + id);
		return j;
	}
	
	/**
	 * 删除Model
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("act:model:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			actModelService.delete(id);
		}
		AjaxJson j = new AjaxJson();
		j.setMsg("删除成功" );
		return j;
	}
}
