package com.jeeplus.modules.monitor.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SystemInfo {
	public static Map SystemProperty() {
		Map<String, Comparable> monitorMap = new HashMap();
		Runtime r = Runtime.getRuntime();
		Properties props = System.getProperties();
		InetAddress addr = null;
		String ip = "";
		String hostName = "";
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			ip = "无法获取主机IP";
			hostName = "无法获取主机名";
		}
		if (null != addr) {
			try {
				ip = addr.getHostAddress();
			} catch (Exception e) {
				ip = "无法获取主机IP";
			}
			try {
				hostName = addr.getHostName();
			} catch (Exception e) {
				hostName = "无法获取主机名";
			}
		}
		monitorMap.put("hostIp", ip);// 本地ip地址
		monitorMap.put("hostName", hostName);// 本地主机名
		monitorMap.put("osName", props.getProperty("os.name"));// 操作系统的名称
		monitorMap.put("arch", props.getProperty("os.arch"));// 操作系统的构架
		monitorMap.put("osVersion", props.getProperty("os.version"));// 操作系统的版本
		monitorMap.put("processors", r.availableProcessors());// JVM可以使用的处理器个数
		monitorMap.put("javaVersion", props.getProperty("java.version"));// Java的运行环境版本
		monitorMap.put("vendor", props.getProperty("java.vendor"));// Java的运行环境供应商
		monitorMap.put("javaUrl", props.getProperty("java.vendor.url"));// Java供应商的URL
		monitorMap.put("javaHome", props.getProperty("java.home"));// Java的安装路径
		monitorMap.put("tmpdir", props.getProperty("java.io.tmpdir"));// 默认的临时文件路径
		return monitorMap;
	}



}
