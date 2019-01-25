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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.jxc.entity.Product;
import com.jeeplus.modules.jxc.service.ProductService;

/**
 * 商品信息Controller
 * @author FxLsoft
 * @version 2018-12-22
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;
	
	@ModelAttribute
	public Product get(@RequestParam(required=false) String id) {
		Product entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productService.get(id);
		}
		if (entity == null){
			entity = new Product();
		}
		return entity;
	}
	
	/**
	 * 商品信息列表页面
	 */
	@RequiresPermissions("jxc:product:list")
	@RequestMapping(value = {"list", ""})
	public String list(Product product, Model model) {
		model.addAttribute("product", product);
		return "modules/jxc/productList";
	}
	
		/**
	 * 商品信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:product:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Product product, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (product.getName() != null && product.getBrevityCode() == null) {
			product.setBrevityCode(product.getName());
		}
		Page<Product> page = productService.findPage(new Page<Product>(request, response), product); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商品信息表单页面
	 */
	@RequiresPermissions(value={"jxc:product:view","jxc:product:add","jxc:product:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Product product, Model model) {
		model.addAttribute("product", product);
		model.addAttribute("mode", mode);
		return "modules/jxc/productForm";
	}

	/**
	 * 保存商品信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:product:add","jxc:product:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Product product, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(product);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		productService.save(product);//保存
		j.setSuccess(true);
		j.setMsg("保存商品信息成功");
		return j;
	}
	
	/**
	 * 删除商品信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:product:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Product product) {
		AjaxJson j = new AjaxJson();
		productService.delete(product);
		j.setMsg("删除商品信息成功");
		return j;
	}
	
	/**
	 * 批量删除商品信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:product:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productService.delete(productService.get(id));
		}
		j.setMsg("删除商品信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:product:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Product product, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Product> page = productService.findPage(new Page<Product>(request, response, -1), product);
    		new ExportExcel("商品信息", Product.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商品信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:product:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Product> list = ei.getDataList(Product.class);
			for (Product product : list){
				try{
					productService.save(product);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条商品信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入商品信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入商品信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:product:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品信息数据导入模板.xlsx";
    		List<Product> list = Lists.newArrayList(); 
    		new ExportExcel("商品信息数据", Product.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}