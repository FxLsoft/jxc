/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.web.BaseController;

/**
 * 标签Controller
 * @author jeeplus
 * @version 2016-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/tag")
public class TagController extends BaseController {
	
	/**
	 * 树结构选择标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "treeselect")
	public String treeselect(HttpServletRequest request, Model model) {
		model.addAttribute("url", request.getParameter("url")); 	// 树结构数据URL
		model.addAttribute("extId", request.getParameter("extId")); // 排除的编号ID
		model.addAttribute("checked", request.getParameter("checked")); // 是否可复选
		model.addAttribute("selectIds", request.getParameter("selectIds")); // 指定默认选中的ID
		model.addAttribute("isAll", request.getParameter("isAll")); 	// 是否读取全部数据，不进行权限过滤
		model.addAttribute("allowSearch", request.getParameter("allowSearch"));	// 是否显示查找
		return "modules/common/tagTreeselect";
	}
	
	/**
	 * 图标选择标签（iconselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "iconselect")
	public String iconselect(HttpServletRequest request, Model model) {
		model.addAttribute("value", request.getParameter("value"));
		return "modules/common/tagIconselect";
	}
	
	/**
	 * gridselect选择框
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "gridselect")
	public String gridselect(String url, String fieldLabels, String fieldKeys, String searchLabels, String searchKeys, String isMultiSelected, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			fieldLabels = URLDecoder.decode(fieldLabels, "UTF-8");
			fieldKeys = URLDecoder.decode(fieldKeys, "UTF-8");
			searchLabels = URLDecoder.decode(searchLabels, "UTF-8");
			searchKeys = URLDecoder.decode(searchKeys, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("isMultiSelected", isMultiSelected);
		model.addAttribute("fieldLabels", fieldLabels.split("\\|"));
		model.addAttribute("fieldKeys", fieldKeys.split("\\|"));
		model.addAttribute("url", url);
		model.addAttribute("searchLabels", searchLabels.split("\\|"));
		model.addAttribute("searchKeys", searchKeys.split("\\|"));
		return "modules/common/gridselect";
	}


	/**
	 * 文件上传
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "fileUpload")
	public String fileUpload(String fileValues, String readonly, String uploadPath, String type, String fileNumLimit, String fileSizeLimit, String allowedExtensions, Model model) {
		String[] fieldValuesArra = fileValues.split("\\|");
		String fileIds = "";
		String fileSizes = "";
		for(String value: fieldValuesArra){
			fileIds = fileIds + FileUtils.getFileDir(value) + "|";
			fileSizes = fileSizes + FileUtils.getFileSize(FileUtils.getFileDir(value))+ "|";
		}

		if(StringUtils.isNotBlank(fileIds)){
			fileIds = fileIds.substring(0, fileIds.length()-1);
		}
		String mimeTypes = "";
		if("all".equals(type)){
			allowedExtensions = "";
			mimeTypes = "All types(*.*)";
		}else if("file".equals(type)){
			allowedExtensions = "7z,aiff,asf,avi,bmp,csv,doc,docx,fla,flv,gif,gz,gzip,jpeg,jpg,mid,mov,mp3,mp4,mpc,mpeg,mpg,ods,odt,pdf,png,ppt,pptx,pxd,qt,ram,rar,rm,rmi,rmvb,rtf,sdc,sitd,swf,sxc,sxw,tar,tgz,tif,tiff,txt,vsd,wav,wma,wmv,xls,xlsx,zip";
			mimeTypes = ".7z,.aiff,.asf,.avi,.bmp,.csv,.doc,.docx,.fla,.flv,.gif,.gz,.gzip,.jpeg,.jpg,.mid,.mov,.mp3,.mp4,.mpc,.mpeg,.mpg,.ods,.odt,.pdf,.png,.ppt,.pptx,.pxd,.qt,.ram,.rar,.rm,.rmi,.rmvb,.rtf,.sdc,.sitd,.swf,.sxc,.sxw,.tar,.tgz,.tif,.tiff,.txt,.vsd,.wav,.wma,.wmv,.xls,.xlsx,.zip";
		}else if("image".equals(type)){
			allowedExtensions = "gif,jpg,jpeg,bmp,png";
			mimeTypes =  "image/*";
		}else if("audio".equals(type)){
			allowedExtensions = "CD,OGG,MP3,ASF,WMA,WAV,MP3PRO,RM,REAL,APE,MODULE,MIDI,VQF";
			mimeTypes  = "audio/*";
		}else if("video".equals(type)){
			allowedExtensions = "AVI,WMV,RM,RMVB,MPEG1,MPEG2,MPEG4(MP4),3GP,ASF,SWF,VOB,DAT,MOV,M4V,FLV,F4V,MKV,MTS,TS";
			mimeTypes = "video/*";
		}else if("office".equals(type)){
			allowedExtensions = "txt,xls,xlsx,xlsm,xltx,xltm,xlsb,xlam,doc,docx,docm,dotx,dotm,ppt,pptx,pptm,ppsx,ppsm,potx,potm,ppam";
			mimeTypes = ".txt,.xls,.xlsx,.xlsm,.xltx,.xltm,.xlsb,.xlam,.doc,.docx,.docm,.dotx,.dotm,.ppt,.pptx,.pptm,.ppsx,.ppsm,.potx,.potm,.ppam";
		}else {
			String[] ex=allowedExtensions.split(",");
			for(String e:ex){
				mimeTypes = mimeTypes +"."+e+",";
			}
			if(StringUtils.isNotBlank(mimeTypes)){
				mimeTypes = mimeTypes.substring(0,mimeTypes.length()-1);
			}
		}
		model.addAttribute("fileIds", fileIds);
		model.addAttribute("fileValues", fileValues);
		model.addAttribute("fileSizes", fileSizes);
		model.addAttribute("uploadPath", uploadPath);
		model.addAttribute("type", type);
		model.addAttribute("fileNumLimit", fileNumLimit);
		model.addAttribute("fileSizeLimit", fileSizeLimit);
		model.addAttribute("allowedExtensions", allowedExtensions);
		model.addAttribute("mimeTypes", mimeTypes);
		model.addAttribute("readonly", "true".equals(readonly)?true:false);
		return "modules/common/fileUpload";
	}

	/**
	 * 文件上传
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "importExcel")
	public String importExcel() {
		return "modules/common/importExcel";
	}
}
