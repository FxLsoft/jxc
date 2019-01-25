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
import com.jeeplus.modules.jxc.entity.ReturnOrder;
import com.jeeplus.modules.jxc.mapper.ReturnOrderDetailMapper;
import com.jeeplus.modules.jxc.service.ReturnOrderService;

/**
 * 退货单Controller
 * @author 周涛
 * @version 2018-12-24
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/returnOrder")
public class ReturnOrderController extends BaseController {

	@Autowired
	private ReturnOrderService returnOrderService;
	
	@Autowired
	private ReturnOrderDetailMapper returnOrderDetailMapper;
	
	@ModelAttribute
	public ReturnOrder get(@RequestParam(required=false) String id) {
		ReturnOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = returnOrderService.get(id);
		}
		if (entity == null){
			entity = new ReturnOrder();
		}
		return entity;
	}
	
	/**
	 * 退货信息列表页面
	 */
	@RequiresPermissions("jxc:returnOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list(ReturnOrder returnOrder, Model model) {
		model.addAttribute("returnOrder", returnOrder);
		return "modules/jxc/returnOrderList";
	}
	
		/**
	 * 退货信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:returnOrder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ReturnOrder returnOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReturnOrder> page = returnOrderService.findPage(new Page<ReturnOrder>(request, response), returnOrder); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑退货信息表单页面
	 */
	@RequiresPermissions(value={"jxc:returnOrder:view","jxc:returnOrder:add","jxc:returnOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, ReturnOrder returnOrder, Model model) {
		if ("add".equals(mode)) {
			returnOrder.setNo("TH" + IdWorker.getId());
		}
		model.addAttribute("returnOrder", returnOrder);
		model.addAttribute("mode", mode);
		return "modules/jxc/returnOrderForm";
	}

	/**
	 * 保存退货信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:returnOrder:add","jxc:returnOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ReturnOrder returnOrder, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(returnOrder);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		if (returnOrder.getStatus() == null) {
			returnOrder.setStatus(0);
		}
		if (returnOrder.getStatus() == 1) {
			j.setSuccess(false);
			j.setMsg("已提交单不能被修改！");
			return j;
		}
		// 删除已有的详情
		returnOrderDetailMapper.deleteByOrderNo(returnOrder.getNo());
		returnOrderService.save(returnOrder);//保存
		j.setSuccess(true);
		j.setMsg("保存退货信息成功");
		return j;
	}
	
	/**
	 * 删除退货信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:returnOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ReturnOrder returnOrder) {
		AjaxJson j = new AjaxJson();
		returnOrderService.delete(returnOrder);
		j.setMsg("删除退货信息成功");
		return j;
	}
	
	/**
	 * 批量删除退货信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:returnOrder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			returnOrderService.delete(returnOrderService.get(id));
		}
		j.setMsg("删除退货信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:returnOrder:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(ReturnOrder returnOrder, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "退货信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ReturnOrder> page = returnOrderService.findPage(new Page<ReturnOrder>(request, response, -1), returnOrder);
    		new ExportExcel("退货信息", ReturnOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出退货信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public ReturnOrder detail(String id) {
		return returnOrderService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:returnOrder:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ReturnOrder> list = ei.getDataList(ReturnOrder.class);
			for (ReturnOrder returnOrder : list){
				try{
					returnOrderService.save(returnOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条退货信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条退货信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入退货信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入退货信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:returnOrder:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "退货信息数据导入模板.xlsx";
    		List<ReturnOrder> list = Lists.newArrayList(); 
    		new ExportExcel("退货信息数据", ReturnOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}