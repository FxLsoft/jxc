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
import com.jeeplus.modules.jxc.entity.Warehouse;
import com.jeeplus.modules.jxc.service.WarehouseService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 商品仓库Controller
 * @author 周涛
 * @version 2018-12-25
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/warehouse")
public class WarehouseController extends BaseController {

	@Autowired
	private WarehouseService warehouseService;
	
	@ModelAttribute
	public Warehouse get(@RequestParam(required=false) String id) {
		Warehouse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = warehouseService.get(id);
		}
		if (entity == null){
			entity = new Warehouse();
		}
		return entity;
	}
	
	/**
	 * 仓库信息列表页面
	 */
	@RequiresPermissions("jxc:warehouse:list")
	@RequestMapping(value = {"list", ""})
	public String list(Warehouse warehouse, Model model) {
		model.addAttribute("warehouse", warehouse);
		return "modules/jxc/warehouseList";
	}
	
		/**
	 * 仓库信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:warehouse:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Warehouse warehouse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Warehouse> page = warehouseService.findPage(new Page<Warehouse>(request, response), warehouse); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑仓库信息表单页面
	 */
	@RequiresPermissions(value={"jxc:warehouse:view","jxc:warehouse:add","jxc:warehouse:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Warehouse warehouse, Model model) {
		model.addAttribute("warehouse", warehouse);
		model.addAttribute("mode", mode);
		return "modules/jxc/warehouseForm";
	}

	/**
	 * 保存仓库信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:warehouse:add","jxc:warehouse:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Warehouse warehouse, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(warehouse);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		warehouse.setCompany(UserUtils.getUser().getCompany().getName());
		warehouseService.save(warehouse);//保存
		j.setSuccess(true);
		j.setMsg("保存仓库信息成功");
		return j;
	}
	
	/**
	 * 删除仓库信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:warehouse:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Warehouse warehouse) {
		AjaxJson j = new AjaxJson();
		warehouseService.delete(warehouse);
		j.setMsg("删除仓库信息成功");
		return j;
	}
	
	/**
	 * 批量删除仓库信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:warehouse:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			warehouseService.delete(warehouseService.get(id));
		}
		j.setMsg("删除仓库信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:warehouse:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Warehouse warehouse, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "仓库信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Warehouse> page = warehouseService.findPage(new Page<Warehouse>(request, response, -1), warehouse);
    		new ExportExcel("仓库信息", Warehouse.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出仓库信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:warehouse:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Warehouse> list = ei.getDataList(Warehouse.class);
			for (Warehouse warehouse : list){
				try{
					warehouseService.save(warehouse);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条仓库信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条仓库信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入仓库信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入仓库信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:warehouse:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "仓库信息数据导入模板.xlsx";
    		List<Warehouse> list = Lists.newArrayList(); 
    		new ExportExcel("仓库信息数据", Warehouse.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}