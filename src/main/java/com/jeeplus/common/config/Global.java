/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.common.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;

import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.core.web.Servlets;
import com.jeeplus.modules.sys.security.SystemAuthorizingRealm;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.apache.ibatis.io.Resources;
import org.springframework.core.io.DefaultResourceLoader;

import com.google.common.collect.Maps;
import com.jeeplus.common.utils.PropertiesLoader;
import com.jeeplus.common.utils.StringUtils;

/**
 * 全局配置类
 * @author jeeplus
 * @version 2017-06-25
 */
public class Global {

	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("/properties/app.properties");

	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";
	
	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	/**
	 * 上传文件基础虚拟路径
	 */
	public static final String USERFILES_BASE_URL = "/userfiles/";

	/**
	 * 共享文档物理存储地址
	 * @return
	 */
	public static String getShareBaseDir(){
		String dir =  Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL  + "共享文档/";
		FileUtils.createDirectory(dir);
		return dir;
	}
	/**
	 * 共享文档网络访问地址
	 * @return
	 */
	public static String getShareBaseUrl(){
		SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) UserUtils.getPrincipal();
		return  Servlets.getRequest().getContextPath() + Global.USERFILES_BASE_URL +  "/共享文档/";
	}

	/**
	 * 我的文档物理存储地址
	 * @return
	 */
	public static String getMyDocDir(){
		SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) UserUtils.getPrincipal();
		String dir = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL + principal + "/我的文档/";
		FileUtils.createDirectory(dir);
		return dir;
	}
	/**
	 * 我的文档网络访问地址
	 * @return
	 */
	public static String getMyDocUrl(){
		SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) UserUtils.getPrincipal();
		return  Servlets.getRequest().getContextPath() + Global.USERFILES_BASE_URL + principal + "/我的文档/";
	}

	/**
	 * 程序附件物理存储地址
	 * @return
	 */
	public static String getAttachmentDir(){
		SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) UserUtils.getPrincipal();
		String dir = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL + principal + "/程序附件/";
		FileUtils.createDirectory(dir);
		return dir;
	}

	/**
	 * 程序附件网络访问地址
	 * @return
	 */
	public static String getAttachmentUrl(){

		SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) UserUtils.getPrincipal();
		return  Servlets.getRequest().getContextPath() + Global.USERFILES_BASE_URL + principal + "/程序附件/";
	}

	/**
	 * 绝对地址转换为网络地址
	 * @return
	 */
	public static String transDirToUrl(String dir){
		return   Servlets.getRequest().getContextPath()+"/" + dir.substring(Global.getUserfilesBaseDir().length());
	}
	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}
	
	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}


	/**
	 * 获取管理端根路径
	 */
	public static String getDefaultTheme() {
		return getConfig("defaultTheme");
	}
	
	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}
	
	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}
	
	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}
	
	/**
	 * 在修改系统用户和角色时是否同步到Activiti
	 */
	public static Boolean isSynActivitiIndetity() {
		String dm = getConfig("activiti.isSynActivitiIndetity");
		return "true".equals(dm) || "1".equals(dm);
	}
    
	/**
	 * 页面获取常量
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public static String getUserfilesBaseDir() {
		String dir = getConfig("userfiles.basedir");
		if (StringUtils.isBlank(dir)){
			return "";
		}
		if(!dir.endsWith("/")) {
			dir += "/";
		}
//		System.out.println("userfiles.basedir: " + dir);
		return dir;
	}
	
    /**
     * 获取工程路径
     * @return
     */
    public static String getProjectPath(){
    	// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
    }
    
    /**
	 * 写入properties信息
	 * 
	 * @param key
	 *            名称
	 * @param value
	 *            值
	 */
	public static void modifyConfig(String key, String value) {
		try {
			// 从输入流中读取属性列表（键和元素对）
			Properties prop = getProperties();
			prop.setProperty(key, value);
			String path = Global.class.getResource("/properties/app.properties").getPath();
			FileOutputStream outputFile = new FileOutputStream(path);
			prop.store(outputFile, "modify");
			outputFile.close();
			outputFile.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回　Properties
	 * @param
	 * @return
	 */
	public static Properties getProperties(){
		Properties prop = new Properties();
		try {
			Reader reader = Resources.getResourceAsReader("/properties/app.properties");
			prop.load(reader);
		} catch (Exception e) {
			return null;
		}
		return prop;
	}

	
}
