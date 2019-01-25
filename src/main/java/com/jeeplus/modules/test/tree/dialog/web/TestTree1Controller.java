/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.tree.dialog.web;

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
import com.jeeplus.modules.test.tree.dialog.entity.TestTree1;
import com.jeeplus.modules.test.tree.dialog.service.TestTree1Service;

/**
 * 组织机构Controller
 * @author liugf
 * @version 2018-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/test/tree/dialog/testTree1")
public class TestTree1Controller extends BaseController {

	@Autowired
	private TestTree1Service testTree1Service;
	
	@ModelAttribute
	public TestTree1 get(@RequestParam(required=false) String id) {
		TestTree1 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testTree1Service.get(id);
		}
		if (entity == null){
			entity = new TestTree1();
		}
		return entity;
	}
	
	/**
	 * 机构列表页面
	 */
	@RequiresPermissions("test:tree:dialog:testTree1:list")
	@RequestMapping(value = {"list", ""})
	public String list(TestTree1 testTree1,  HttpServletRequest request, HttpServletResponse response, Model model) {
	
		model.addAttribute("testTree1", testTree1);
		return "modules/test/tree/dialog/testTree1List";
	}

	/**
	 * 查看，增加，编辑机构表单页面
	 */
	@RequiresPermissions(value={"test:tree:dialog:testTree1:view","test:tree:dialog:testTree1:add","test:tree:dialog:testTree1:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TestTree1 testTree1, Model model) {
		if (testTree1.getParent()!=null && StringUtils.isNotBlank(testTree1.getParent().getId())){
			testTree1.setParent(testTree1Service.get(testTree1.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(testTree1.getId())){
				TestTree1 testTree1Child = new TestTree1();
				testTree1Child.setParent(new TestTree1(testTree1.getParent().getId()));
				List<TestTree1> list = testTree1Service.findList(testTree1); 
				if (list.size() > 0){
					testTree1.setSort(list.get(list.size()-1).getSort());
					if (testTree1.getSort() != null){
						testTree1.setSort(testTree1.getSort() + 30);
					}
				}
			}
		}
		if (testTree1.getSort() == null){
			testTree1.setSort(30);
		}
		model.addAttribute("testTree1", testTree1);
		return "modules/test/tree/dialog/testTree1Form";
	}

	/**
	 * 保存机构
	 */
	@ResponseBody
	@RequiresPermissions(value={"test:tree:dialog:testTree1:add","test:tree:dialog:testTree1:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TestTree1 testTree1, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(testTree1);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		testTree1Service.save(testTree1);//保存
		j.setSuccess(true);
		j.put("testTree1", testTree1);
		j.setMsg("保存机构成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<TestTree1> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return testTree1Service.getChildren(parentId);
	}
	
	/**
	 * 删除机构
	 */
	@ResponseBody
	@RequiresPermissions("test:tree:dialog:testTree1:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TestTree1 testTree1) {
		AjaxJson j = new AjaxJson();
		testTree1Service.delete(testTree1);
		j.setSuccess(true);
		j.setMsg("删除机构成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<TestTree1> list = testTree1Service.findList(new TestTree1());
		for (int i=0; i<list.size(); i++){
			TestTree1 e = list.get(i);
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