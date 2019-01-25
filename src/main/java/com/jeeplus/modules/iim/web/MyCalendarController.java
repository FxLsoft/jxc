/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.iim.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.iim.entity.MyCalendar;
import com.jeeplus.modules.iim.service.MyCalendarService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 日历Controller
 * 
 * @author liugf
 * @version 2016-04-19
 */
@Controller
@RequestMapping(value = "${adminPath}/iim/myCalendar")
public class MyCalendarController extends BaseController {

	@Autowired
	private MyCalendarService myCalendarService;

	@ModelAttribute
	public MyCalendar get(@RequestParam(required = false) String id) {
		MyCalendar entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = myCalendarService.get(id);
		}
		if (entity == null) {
			entity = new MyCalendar();
		}
		return entity;
	}

	/**
	 * 日历页面
	 */
	@RequestMapping(value = { "index", "" })
	public String index(MyCalendar myCalendar, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "modules/iim/calendar/myCalendar";
	}

	/**
	 * 新建日历
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "add")
	public AjaxJson add(MyCalendar myCalendar, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		AjaxJson j = new AjaxJson();
		myCalendar.setUser(UserUtils.getUser());
		myCalendarService.save(myCalendar);
		
		j.setSuccess(true);
		j.setMsg("保存日历成功");
		j.put("myCalendar", myCalendar);

		return j;
	}

	/**
	 * 查看，增加，编辑日历信息表单页面
	 */
	@RequestMapping(value = "form")
	public String editform(MyCalendar myCalendar, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute("myCalendar", myCalendar);
		return "modules/iim/calendar/myCalendarForm";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "findList")
	protected List<MyCalendar> doPost(MyCalendar myCalendar,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws ServletException, IOException {
		myCalendar.setUser(UserUtils.getUser());
		List<MyCalendar> list = myCalendarService.findList(myCalendar);

		return list;
	}


	/**
	 * 编辑日历
	 * @throws Exception 
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(MyCalendar myCalendar, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		AjaxJson j = new AjaxJson();
		myCalendar.setUser(UserUtils.getUser());

        myCalendarService.save(myCalendar);//保存
        j.setSuccess(true);
        j.setMsg("保存成功!");
        return j;
	}

	/**
	 * 删除日历
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "del")
	public AjaxJson del(MyCalendar myCalendar,
			RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		myCalendarService.delete(myCalendar);
		j.setSuccess(true);
		j.setMsg("删除成功!");
		return j;

	}

	/**
	 * 縮放日歷
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "resize")
	public AjaxJson resize(MyCalendar myCalendar, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		AjaxJson j = new AjaxJson();
		Integer daydiff = Integer.parseInt(request.getParameter("daydiff")) * 24 * 60 * 60;
		Integer minudiff = Integer.parseInt(request.getParameter("minudiff"))/1000;
		Date start = myCalendar.getStart();
		long lstart = start.getTime()/1000;
		Date end = myCalendar.getEnd();
		Integer difftime = daydiff + minudiff;
		if (end == null) {
			myCalendar.setEnd(DateUtils.parseDate(DateUtils.long2string(lstart + difftime)));
			myCalendar.setUser(UserUtils.getUser());
			myCalendarService.save(myCalendar);
		} else {
			long lend = end.getTime()/1000;
			myCalendar.setEnd(DateUtils.parseDate(DateUtils.long2string(lend + difftime)));
			myCalendar.setUser(UserUtils.getUser());
			myCalendarService.save(myCalendar);
		}
		j.setSuccess(true);
		j.setMsg("保存成功！");
		return j;
	}

	/**
	 * 拖拽日历
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "drag")
	public AjaxJson drag(MyCalendar myCalendar, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		AjaxJson j = new AjaxJson();
		Integer daydiff = Integer.parseInt(request.getParameter("daydiff")) * 24 * 60 * 60;
		Integer minudiff = Integer.parseInt(request.getParameter("minudiff"))/1000;
		Date start = myCalendar.getStart();
		long lstart = start.getTime()/1000;
		Date end = myCalendar.getEnd();
			Integer difftime = daydiff + minudiff;
			if (end == null) {
				myCalendar.setStart(DateUtils.parseDate(DateUtils.long2string(lstart + difftime)));
				myCalendar.setUser(UserUtils.getUser());
				myCalendarService.save(myCalendar);
			} else {
				long lend = end.getTime()/1000;
				myCalendar.setStart(DateUtils.parseDate(DateUtils.long2string(lstart + difftime)));
				myCalendar.setEnd(DateUtils.parseDate(DateUtils.long2string(lend + difftime)));
				myCalendar.setUser(UserUtils.getUser());
				myCalendarService.save(myCalendar);
			}
		j.setSuccess(true);
		j.setMsg("保存成功！");
		return j;
	}

}