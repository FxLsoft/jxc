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
import com.jeeplus.modules.jxc.entity.StockOrder;
import com.jeeplus.modules.jxc.service.StockOrderService;

/**
 * 入库单Controller
 * @author 周涛
 * @version 2018-12-25
 */
@Controller
@RequestMapping(value = "${adminPath}/jxc/stockOrder")
public class StockOrderController extends BaseController {

	@Autowired
	private StockOrderService stockOrderService;
	
	@ModelAttribute
	public StockOrder get(@RequestParam(required=false) String id) {
		StockOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = stockOrderService.get(id);
		}
		if (entity == null){
			entity = new StockOrder();
		}
		return entity;
	}
	
	/**
	 * 入库单信息列表页面
	 */
	@RequiresPermissions("jxc:stockOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list(StockOrder stockOrder, Model model) {
		model.addAttribute("stockOrder", stockOrder);
		return "modules/jxc/stockOrderList";
	}
	
		/**
	 * 入库单信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jxc:stockOrder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(StockOrder stockOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StockOrder> page = stockOrderService.findPage(new Page<StockOrder>(request, response), stockOrder); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑入库单信息表单页面
	 */
	@RequiresPermissions(value={"jxc:stockOrder:view","jxc:stockOrder:add","jxc:stockOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, StockOrder stockOrder, Model model) {
		if ("add".equals(mode)) {
			stockOrder.setNo("RK" + IdWorker.getId());
		}
		model.addAttribute("stockOrder", stockOrder);
		model.addAttribute("mode", mode);
		return "modules/jxc/stockOrderForm";
	}

	/**
	 * 保存入库单信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jxc:stockOrder:add","jxc:stockOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(StockOrder stockOrder, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(stockOrder);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		if (stockOrder.getStatus() == null) {
			stockOrder.setStatus(0);
		}
		if (stockOrder.getStatus() == 1) {
			j.setSuccess(false);
			j.setMsg("已提交单不能被修改！");
			return j;
		}
		stockOrderService.save(stockOrder);//保存
		j.setSuccess(true);
		j.setMsg("保存入库单信息成功");
		return j;
	}
	
	/**
	 * 删除入库单信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:stockOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(StockOrder stockOrder) {
		AjaxJson j = new AjaxJson();
		stockOrderService.delete(stockOrder);
		j.setMsg("删除入库单信息成功");
		return j;
	}
	
	/**
	 * 批量删除入库单信息
	 */
	@ResponseBody
	@RequiresPermissions("jxc:stockOrder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			stockOrderService.delete(stockOrderService.get(id));
		}
		j.setMsg("删除入库单信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jxc:stockOrder:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(StockOrder stockOrder, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "入库单信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<StockOrder> page = stockOrderService.findPage(new Page<StockOrder>(request, response, -1), stockOrder);
    		new ExportExcel("入库单信息", StockOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出入库单信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public StockOrder detail(String id) {
		return stockOrderService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jxc:stockOrder:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<StockOrder> list = ei.getDataList(StockOrder.class);
			for (StockOrder stockOrder : list){
				try{
					stockOrderService.save(stockOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条入库单信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条入库单信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入入库单信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入入库单信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jxc:stockOrder:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "入库单信息数据导入模板.xlsx";
    		List<StockOrder> list = Lists.newArrayList(); 
    		new ExportExcel("入库单信息数据", StockOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}