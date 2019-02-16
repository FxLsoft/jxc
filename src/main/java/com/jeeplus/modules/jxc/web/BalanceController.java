/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.web;

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
import com.jeeplus.modules.jxc.entity.Balance;
import com.jeeplus.modules.jxc.service.BalanceService;

/**
 * 电子秤管理Controller
 * @author FxLsoft
 * @version 2019-02-15
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/balance")
public class BalanceController extends BaseController {

	@Autowired
	private BalanceService balanceService;
	
	@ModelAttribute
	public Balance get(@RequestParam(required=false) String id) {
		Balance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = balanceService.get(id);
		}
		if (entity == null){
			entity = new Balance();
		}
		return entity;
	}
	
	/**
	 * 电子秤信息列表页面
	 */
	@RequiresPermissions("jxc:balance:list")
	@RequestMapping(value = {"list", ""})
	public String list(Balance balance, Model model) {
		model.addAttribute("balance", balance);
		return "modules/jxc/balanceList";
	}
	
		/**
	 * 电子秤信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:balance:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Balance balance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Balance> page = balanceService.findPage(new Page<Balance>(request, response), balance); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑电子秤信息表单页面
	 */
	@RequiresPermissions(value={"jxc:balance:view","jxc:balance:add","jxc:balance:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Balance balance, Model model) {
		model.addAttribute("balance", balance);
		model.addAttribute("mode", mode);
		return "modules/jxc/balanceForm";
	}

	/**
	 * 保存电子秤信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:balance:add","jxc:balance:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Balance balance, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(balance);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		balanceService.save(balance);//保存
		j.setSuccess(true);
		j.setMsg("保存电子秤信息成功");
		return j;
	}
	
	/**
	 * 删除电子秤信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:balance:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Balance balance) {
		AjaxJson j = new AjaxJson();
		balanceService.delete(balance);
		j.setMsg("删除电子秤信息成功");
		return j;
	}
	
	/**
	 * 批量删除电子秤信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:balance:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			balanceService.delete(balanceService.get(id));
		}
		j.setMsg("删除电子秤信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:balance:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Balance balance, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "电子秤信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Balance> page = balanceService.findPage(new Page<Balance>(request, response, -1), balance);
    		new ExportExcel("电子秤信息", Balance.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出电子秤信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:balance:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Balance> list = ei.getDataList(Balance.class);
			for (Balance balance : list){
				try{
					balanceService.save(balance);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条电子秤信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条电子秤信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入电子秤信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入电子秤信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:balance:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "电子秤信息数据导入模板.xlsx";
    		List<Balance> list = Lists.newArrayList(); 
    		new ExportExcel("电子秤信息数据", Balance.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}