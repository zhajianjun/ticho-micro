package com.ticho.upms.infrastructure.core.util;

import cn.hutool.core.util.StrUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

import javax.servlet.http.HttpServletRequest;

/**
 * IP工具
 *
 * @author zhajianjun
 * @date 2023-02-08 14:28
 */
public class IpUtil {

    public static final String USER_AGENT = "User-Agent";

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        // Proxy-Client-IP 这个一般是经过apache http服务器的请求才会有，用apache http做代理时一般会加上Proxy-Client-IP请求头，而WL-Proxy-Client-IP是他的weblogic插件加上的头。
        String unknown = "unknown";
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StrUtil.isBlank(ip)) {
            return "";
        }
        int index = ip.indexOf(",");
        if (index != -1) {
            return ip.substring(0, index);
        } else {
            return ip;
        }
    }

    /**
     * 获取发起请求的浏览器名称
     */
    public static String getBrowserName(HttpServletRequest request) {
        try {
            String header = request.getHeader(USER_AGENT);
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            Browser browser = userAgent.getBrowser();
            return browser.getName();

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取发起请求的浏览器版本号
     */
    public static String getBrowserVersion(HttpServletRequest request) {
        try {
            String header = request.getHeader(USER_AGENT);
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            //获取浏览器信息
            Browser browser = userAgent.getBrowser();
            //获取浏览器版本号
            Version version = browser.getVersion(header);
            return version.getVersion();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取发起请求的操作系统名称
     */
    public static String getOsName(HttpServletRequest request) {
        try {
            String header = request.getHeader(USER_AGENT);
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            OperatingSystem operatingSystem = userAgent.getOperatingSystem();
            return operatingSystem.getName();
        } catch (Exception e) {
            return null;
        }
    }

}
