/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.common.utils;

import net.oschina.j2cache.CacheObject;
import net.oschina.j2cache.J2Cache;

/**
 * Cache工具类
 * @author jeeplus
 * @version 2017-1-19
 */
public class CacheUtils {
	

	private static final String SYS_REGION = "sysCache";

	
	
	/**
	 * 获取SYS_CACHE缓存
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return get(SYS_REGION, key);
	}
	
	/**
	 * 写入SYS_CACHE缓存
	 * @param key
	 * @return
	 */
	public static void put(String key, Object value) {
		put(SYS_REGION, key, value);
	}
	
	/**
	 * 从SYS_CACHE缓存中移除
	 * @param key
	 * @return
	 */
	public static void remove(String key) {
		remove(SYS_REGION, key);
	}
	
	/**
	 * 获取缓存
	 * @param key
	 * @return
	 */
	public static Object get(String region, String key) {
		CacheObject object = J2Cache.getChannel().get(region ,key);
		return object==null?null:object.getValue();
	}
	
	
	

	/**
	 * 写入缓存
	 * @param region
	 * @param key
	 * @param value
	 */
	public static void put(String region, String key, Object value) {
		J2Cache.getChannel().set(region, key, value);

	}

	/**
	 * 从缓存中移除
	 * @param region
	 * @param key
	 */
	public static void remove(String region, String key) {
		J2Cache.getChannel().evict(region, key);
	}

	
}
