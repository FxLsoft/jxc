/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.common.utils;

/**
 * Created by 刘高峰 on 2018/6/21.
 */
public class MobileUtils {
    private final static String[] agent = { "Android", "iPhone", "iPod","iPad", "Windows Phone", "MQQBrowser" }; //定义移动端请求的所有可能类型
    /**
     * 判断User-Agent 是不是来自于手机
     * @param ua
     * @return
     */
    public static boolean checkAgentIsMobile(String ua) {
        boolean flag = false;
        if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
// 排除 苹果桌面系统
            if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
                for (String item : agent) {
                    if (ua.contains(item)) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }
}
