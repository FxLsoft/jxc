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
import com.jeeplus.modules.jxc.entity.BalanceSale;
import com.jeeplus.modules.jxc.service.BalanceSaleService;

/**
 * 电子秤销售Controller
 * @author FxLsoft
 * @version 2019-02-11
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/balanceSale")
public class BalanceSaleController extends BaseController {

	@Autowired
	private BalanceSaleService balanceSaleService;
	
	@ModelAttribute
	public BalanceSale get(@RequestParam(required=false) String id) {
		BalanceSale entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = balanceSaleService.get(id);
		}
		if (entity == null){
			entity = new BalanceSale();
		}
		return entity;
	}
	
	/**
	 * 电子秤销售列表页面
	 */
	@RequiresPermissions("jxc:balanceSale:list")
	@RequestMapping(value = {"list", ""})
	public String list(BalanceSale balanceSale, Model model) {
		model.addAttribute("balanceSale", balanceSale);
		return "modules/jxc/balanceSaleList";
	}
	
		/**
	 * 电子秤销售列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:balanceSale:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BalanceSale balanceSale, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BalanceSale> page = balanceSaleService.findPage(new Page<BalanceSale>(request, response), balanceSale); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑电子秤销售表单页面
	 */
	@RequiresPermissions(value={"jxc:balanceSale:view","jxc:balanceSale:add","jxc:balanceSale:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BalanceSale balanceSale, Model model) {
		model.addAttribute("balanceSale", balanceSale);
		model.addAttribute("mode", mode);
		return "modules/jxc/balanceSaleForm";
	}

	/**
	 * 保存电子秤销售
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:balanceSale:add","jxc:balanceSale:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BalanceSale balanceSale, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(balanceSale);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		balanceSaleService.save(balanceSale);//保存
		j.setSuccess(true);
		j.setMsg("保存电子秤销售成功");
		return j;
	}
	
	/**
	 * 删除电子秤销售
	 */
	@ResponseBody
	@RequiresPermissions("jxc:balanceSale:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(BalanceSale balanceSale) {
		AjaxJson j = new AjaxJson();
		balanceSaleService.delete(balanceSale);
		j.setMsg("删除电子秤销售成功");
		return j;
	}
	
	/**
	 * 批量删除电子秤销售
	 */
	@ResponseBody
	@RequiresPermissions("jxc:balanceSale:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			balanceSaleService.delete(balanceSaleService.get(id));
		}
		j.setMsg("删除电子秤销售成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:balanceSale:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BalanceSale balanceSale, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "电子秤销售"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BalanceSale> page = balanceSaleService.findPage(new Page<BalanceSale>(request, response, -1), balanceSale);
    		new ExportExcel("电子秤销售", BalanceSale.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出电子秤销售记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:balanceSale:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BalanceSale> list = ei.getDataList(BalanceSale.class);
			for (BalanceSale balanceSale : list){
				try{
					balanceSaleService.save(balanceSale);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条电子秤销售记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条电子秤销售记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入电子秤销售失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入电子秤销售数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:balanceSale:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "电子秤销售数据导入模板.xlsx";
    		List<BalanceSale> list = Lists.newArrayList(); 
    		new ExportExcel("电子秤销售数据", BalanceSale.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}