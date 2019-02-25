/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.jxc.entity.Customer;
import com.jeeplus.modules.jxc.service.CustomerService;

/**
 * 客户管理Controller
 * @author FxLsoft
 * @version 2019-02-23
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/customer")
public class CustomerController extends BaseController {

	@Autowired
	private CustomerService customerService;
	
	@ModelAttribute
	public Customer get(@RequestParam(required=false) String id) {
		Customer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerService.get(id);
		}
		if (entity == null){
			entity = new Customer();
		}
		return entity;
	}
	
	/**
	 * 客户信息列表页面
	 */
	@RequiresPermissions("jxc:customer:list")
	@RequestMapping(value = {"list", ""})
	public String list(Customer customer, Model model) {
		model.addAttribute("customer", customer);
		return "modules/jxc/customerList";
	}
	
		/**
	 * 客户信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:customer:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Customer customer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Customer> page = customerService.findPage(new Page<Customer>(request, response), customer); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑客户信息表单页面
	 */
	@RequiresPermissions(value={"jxc:customer:view","jxc:customer:add","jxc:customer:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Customer customer, Model model) {
		model.addAttribute("customer", customer);
		model.addAttribute("mode", mode);
		return "modules/jxc/customerForm";
	}

	/**
	 * 保存客户信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:customer:add","jxc:customer:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Customer customer, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(customer);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		customerService.save(customer);//保存
		j.setSuccess(true);
		j.setMsg("保存客户信息成功");
		return j;
	}
	
	/**
	 * 删除客户信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:customer:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Customer customer) {
		AjaxJson j = new AjaxJson();
		customerService.delete(customer);
		j.setMsg("删除客户信息成功");
		return j;
	}
	
	/**
	 * 批量删除客户信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:customer:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			customerService.delete(customerService.get(id));
		}
		j.setMsg("删除客户信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:customer:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Customer customer, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客户信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Customer> page = customerService.findPage(new Page<Customer>(request, response, -1), customer);
    		new ExportExcel("客户信息", Customer.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客户信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:customer:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Customer> list = ei.getDataList(Customer.class);
			for (Customer customer : list){
				try{
					customerService.save(customer);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条客户信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条客户信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入客户信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入客户信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:customer:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客户信息数据导入模板.xlsx";
    		List<Customer> list = Lists.newArrayList(); 
    		new ExportExcel("客户信息数据", Customer.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}