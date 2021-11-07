package com.ticho.core.mvc.converter;

/**
 *
 * @author AdoroTutto
 * @date 2021-10-27 0:25
 */
public class ConvertConsant {

    /**
     * HH:mm:ss
     */
    public static final String HH_MM_SS = "HH:mm:ss";

    /**
     * HH:mm:ss 正则匹配
     */
    public static final String HH_MM_SS_REGEX = "^\\d{2}:\\d{2}:\\d{2}$";

    /**
     * yyyy-MM-dd
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * yyyy-MM-dd 正则匹配
     */
    public static final String YYYY_MM_DD_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd HH:mm:ss 正则匹配
     */
    public static final String YYYY_MM_DD_HH_MM_SS_REGEX = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";
}
