package com.ticho.core.mvc.util;


import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;

/**
 * 雪花算法
 *
 * @author zzxadi
 * @date
 */
public class SnowFlakeUtil {

    public static Long generateId() {
        return IdUtil.getSnowflake(0L, 0L).nextId();
    }

    public static String generateStrId() {
        return IdUtil.getSnowflake(0L, 0L).nextIdStr();
    }

    public static void main(String[] args) {
        Console.log(generateStrId());
    }
}
