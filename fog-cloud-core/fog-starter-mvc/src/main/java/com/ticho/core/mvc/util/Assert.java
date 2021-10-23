package com.ticho.core.mvc.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ticho.core.mvc.enums.IResultCode;
import com.ticho.core.mvc.exception.ServiceException;

/**
 * 断言工具类
 *
 * @author AdoroTutto
 * @date 2021-10-23 22:43
 */
public class Assert {
    private Assert() {
    }

    public static void isTrue(boolean condition, IResultCode resultCode) {
        if (!condition) {
            cast(resultCode);
        }
    }

    public static void isTrue(boolean condition, IResultCode resultCode, String errMsg) {
        if (!condition) {
            cast(resultCode, errMsg);
        }
    }

    /*  ----------- ------------- */


    public static void isNull(Object obj, IResultCode resultCode) {
        if (ObjectUtil.isNotNull(obj)) {
            cast(resultCode);
        }
    }

    public static void isNull(Object obj, IResultCode resultCode, String errMsg) {
        if (ObjectUtil.isNotNull(obj)) {
            cast(resultCode, errMsg);
        }
    }

    /*  ----------- ------------- */

    public static void isNotNull(Object obj, IResultCode resultCode) {
        if (ObjectUtil.isNull(obj)) {
            cast(resultCode);
        }
    }

    public static void isNotNull(Object obj, IResultCode resultCode, String errMsg) {
        if (ObjectUtil.isNull(obj)) {
            cast(resultCode, errMsg);
        }
    }

    /*  ----------- ------------- */


    public static void isEmpty(Object obj, IResultCode resultCode) {
        if (ObjectUtil.isNotEmpty(obj)) {
            cast(resultCode);
        }
    }

    public static void isEmpty(Object obj, IResultCode resultCode, String errMsg) {
        if (ObjectUtil.isNotEmpty(obj)) {
            cast(resultCode, errMsg);
        }
    }

    /*  ----------- ------------- */

    public static void isNotEmpty(Object obj, IResultCode resultCode) {
        if (ObjectUtil.isEmpty(obj)) {
            cast(resultCode);
        }
    }

    public static void isNotEmpty(Object obj, IResultCode resultCode, String errMsg) {
        if (ObjectUtil.isEmpty(obj)) {
            cast(resultCode, errMsg);
        }
    }

    /*  ----------- ------------- */


    public static void isBlank(String obj, IResultCode resultCode) {
        if (StrUtil.isNotBlank(obj)) {
            cast(resultCode);
        }
    }

    public static void isBlank(String obj, IResultCode resultCode, String errMsg) {
        if (StrUtil.isNotBlank(obj)) {
            cast(resultCode, errMsg);
        }
    }

    /*  ----------- ------------- */

    public static void isNotBlank(String obj, IResultCode resultCode) {
        if (StrUtil.isBlank(obj)) {
            cast(resultCode);
        }
    }

    public static void isNotBlank(String obj, IResultCode resultCode, String errMsg) {
        if (StrUtil.isBlank(obj)) {
            cast(resultCode, errMsg);
        }
    }

    /*  ----------- ------------- */

    public static void cast(IResultCode resultCode) {
        throw new ServiceException(resultCode);
    }

    public static void cast(IResultCode resultCode, String errMsg) {
        throw new ServiceException(resultCode, errMsg);
    }

}
