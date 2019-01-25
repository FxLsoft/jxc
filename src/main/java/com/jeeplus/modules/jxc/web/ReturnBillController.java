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
import com.jeeplus.common.utils.IdWorker;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.jxc.entity.ReturnBill;
import com.jeeplus.modules.jxc.service.ReturnBillService;

/**
 * 退货单据Controller
 * @author FxLsoft
 * @version 2018-12-23
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/returnBill")
public class ReturnBillController extends BaseController {

	@Autowired
	private ReturnBillService returnBillService;
	
	@ModelAttribute
	public ReturnBill get(@RequestParam(required=false) String id) {
		ReturnBill entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = returnBillService.get(id);
		}
		if (entity == null){
			entity = new ReturnBill();
		}
		return entity;
	}
	
	/**
	 * 单据列表页面
	 */
	@RequiresPermissions("jxc:returnBill:list")
	@RequestMapping(value = {"list", ""})
	public String list(ReturnBill returnBill, Model model) {
		model.addAttribute("returnBill", returnBill);
		return "modules/jxc/returnBillList";
	}
	
		/**
	 * 单据列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:returnBill:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ReturnBill returnBill, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReturnBill> page = returnBillService.findPage(new Page<ReturnBill>(request, response), returnBill); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑单据表单页面
	 */
	@RequiresPermissions(value={"jxc:returnBill:view","jxc:returnBill:add","jxc:returnBill:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, ReturnBill returnBill, Model model) {
		if ("add".equals(mode)) {
			returnBill.setNo("TK" + IdWorker.getId());
		}
		model.addAttribute("returnBill", returnBill);
		model.addAttribute("mode", mode);
		return "modules/jxc/returnBillForm";
	}

	/**
	 * 保存单据
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:returnBill:add","jxc:returnBill:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ReturnBill returnBill, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(returnBill);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		if (returnBill.getStatus() == null) {
			returnBill.setStatus(0);
		}
		if (returnBill.getStatus() == 1) {
			j.setSuccess(false);
			j.setMsg("已提交单不能被修改！");
			return j;
		}
		returnBillService.save(returnBill);//保存
		j.setSuccess(true);
		j.setMsg("保存单据成功");
		return j;
	}
	
	/**
	 * 删除单据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:returnBill:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ReturnBill returnBill) {
		AjaxJson j = new AjaxJson();
		returnBillService.delete(returnBill);
		j.setMsg("删除单据成功");
		return j;
	}
	
	/**
	 * 批量删除单据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:returnBill:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			returnBillService.delete(returnBillService.get(id));
		}
		j.setMsg("删除单据成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:returnBill:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(ReturnBill returnBill, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "单据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ReturnBill> page = returnBillService.findPage(new Page<ReturnBill>(request, response, -1), returnBill);
    		new ExportExcel("单据", ReturnBill.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出单据记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:returnBill:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ReturnBill> list = ei.getDataList(ReturnBill.class);
			for (ReturnBill returnBill : list){
				try{
					returnBillService.save(returnBill);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条单据记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条单据记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入单据失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入单据数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:returnBill:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "单据数据导入模板.xlsx";
    		List<ReturnBill> list = Lists.newArrayList(); 
    		new ExportExcel("单据数据", ReturnBill.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}