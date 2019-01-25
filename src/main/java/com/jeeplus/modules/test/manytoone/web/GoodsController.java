/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.manytoone.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.jeeplus.modules.test.manytoone.entity.Goods;
import com.jeeplus.modules.test.manytoone.service.GoodsService;

/**
 * 商品Controller
 * @author liugf
 * @version 2018-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/test/manytoone/goods")
public class GoodsController extends BaseController {

	@Autowired
	private GoodsService goodsService;
	
	@ModelAttribute
	public Goods get(@RequestParam(required=false) String id) {
		Goods entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsService.get(id);
		}
		if (entity == null){
			entity = new Goods();
		}
		return entity;
	}
	
	/**
	 * 商品列表页面
	 */
	@RequiresPermissions("test:manytoone:goods:list")
	@RequestMapping(value = {"list", ""})
	public String list(Goods goods, Model model) {
		model.addAttribute("goods", goods);
		return "modules/test/manytoone/goodsList";
	}
	
		/**
	 * 商品列表数据
	 */
	@ResponseBody
	@RequiresPermissions("test:manytoone:goods:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Goods goods, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Goods> page = goodsService.findPage(new Page<Goods>(request, response), goods); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商品表单页面
	 */
	@RequiresPermissions(value={"test:manytoone:goods:view","test:manytoone:goods:add","test:manytoone:goods:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Goods goods, Model model) {
		model.addAttribute("goods", goods);
		return "modules/test/manytoone/goodsForm";
	}

	/**
	 * 保存商品
	 */
	@ResponseBody
	@RequiresPermissions(value={"test:manytoone:goods:add","test:manytoone:goods:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Goods goods, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(goods);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		goodsService.save(goods);//保存
		j.setSuccess(true);
		j.setMsg("保存商品成功");
		return j;
	}
	
	/**
	 * 删除商品
	 */
	@ResponseBody
	@RequiresPermissions("test:manytoone:goods:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Goods goods) {
		AjaxJson j = new AjaxJson();
		goodsService.delete(goods);
		j.setMsg("删除商品成功");
		return j;
	}
	
	/**
	 * 批量删除商品
	 */
	@ResponseBody
	@RequiresPermissions("test:manytoone:goods:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			goodsService.delete(goodsService.get(id));
		}
		j.setMsg("删除商品成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("test:manytoone:goods:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Goods goods, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Goods> page = goodsService.findPage(new Page<Goods>(request, response, -1), goods);
    		new ExportExcel("商品", Goods.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商品记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("test:manytoone:goods:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Goods> list = ei.getDataList(Goods.class);
			for (Goods goods : list){
				try{
					goodsService.save(goods);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条商品记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入商品失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入商品数据模板
	 */
	@ResponseBody
	@RequiresPermissions("test:manytoone:goods:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品数据导入模板.xlsx";
    		List<Goods> list = Lists.newArrayList(); 
    		new ExportExcel("商品数据", Goods.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}