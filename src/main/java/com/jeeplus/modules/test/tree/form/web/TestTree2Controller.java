/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.tree.form.web;

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
import org.springframework.web.bind.annotation.PathVariable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.config.Global;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.test.tree.form.entity.TestTree2;
import com.jeeplus.modules.test.tree.form.service.TestTree2Service;

/**
 * 组织机构Controller
 * @author liugf
 * @version 2018-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/test/tree/form/testTree2")
public class TestTree2Controller extends BaseController {

	@Autowired
	private TestTree2Service testTree2Service;
	
	@ModelAttribute
	public TestTree2 get(@RequestParam(required=false) String id) {
		TestTree2 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testTree2Service.get(id);
		}
		if (entity == null){
			entity = new TestTree2();
		}
		return entity;
	}
	
	/**
	 * 机构列表页面
	 */
	@RequiresPermissions("test:tree:form:testTree2:list")
	@RequestMapping(value = {"list", ""})
	public String list(TestTree2 testTree2, @ModelAttribute("parentIds") String parentIds, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if(StringUtils.isNotBlank(parentIds)){
			model.addAttribute("parentIds", parentIds);
		}
		model.addAttribute("testTree2", testTree2);
		return "modules/test/tree/form/testTree2List";
	}

	/**
	 * 查看，增加，编辑机构表单页面
	 * params:
	 * 	mode: add, edit, view,addChild 代表四种种模式的页面
	 */
	@RequiresPermissions(value={"test:tree:form:testTree2:view","test:tree:form:testTree2:add","test:tree:form:testTree2:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, TestTree2 testTree2, Model model) {
		if (testTree2.getParent()!=null && StringUtils.isNotBlank(testTree2.getParent().getId())){
			testTree2.setParent(testTree2Service.get(testTree2.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(testTree2.getId())){
				TestTree2 testTree2Child = new TestTree2();
				testTree2Child.setParent(new TestTree2(testTree2.getParent().getId()));
				List<TestTree2> list = testTree2Service.findList(testTree2); 
				if (list.size() > 0){
					testTree2.setSort(list.get(list.size()-1).getSort());
					if (testTree2.getSort() != null){
						testTree2.setSort(testTree2.getSort() + 30);
					}
				}
			}
		}
		if (testTree2.getSort() == null){
			testTree2.setSort(30);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("testTree2", testTree2);
		return "modules/test/tree/form/testTree2Form";
	}

	/**
	 * 保存机构
	 */
	@ResponseBody
	@RequiresPermissions(value={"test:tree:form:testTree2:add","test:tree:form:testTree2:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TestTree2 testTree2, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(testTree2);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		testTree2Service.save(testTree2);//保存
		j.setSuccess(true);
		j.put("parentIds", testTree2.getParentIds());
		j.setMsg("保存机构成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<TestTree2> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return testTree2Service.getChildren(parentId);
	}
	
	/**
	 * 删除机构
	 */
	@ResponseBody
	@RequiresPermissions("test:tree:form:testTree2:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TestTree2 testTree2) {
		AjaxJson j = new AjaxJson();
		testTree2Service.delete(testTree2);
		j.setSuccess(true);
		j.setMsg("删除机构成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<TestTree2> list = testTree2Service.findList(new TestTree2());
		for (int i=0; i<list.size(); i++){
			TestTree2 e = list.get(i);
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