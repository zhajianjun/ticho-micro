package com.ticho.core.mvc.converter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date转换
 * <p>
 *     GET请求，Query查询Date时间类型参数转换
 * </p>
 * @author AdoroTutto
 * @date 2021-10-26 23:57
 */
@Slf4j
@Component
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(@NonNull String source) {
        if (StrUtil.isBlank(source)) {
            return null;
        }
        if (source.matches(ConvertConsant.YYYY_MM_DD_REGEX)) {
            return parseDate(source.trim(), ConvertConsant.YYYY_MM_DD);
        }
        if (source.matches(ConvertConsant.YYYY_MM_DD_HH_MM_SS_REGEX)) {
            return parseDate(source.trim(), ConvertConsant.YYYY_MM_DD_HH_MM_SS);
        }
        throw new IllegalArgumentException("Invalid value '" + source + "'");
    }

    public Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            // SimpleDateFormat优化
            date = new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            log.warn("转换{}为日期(pattern={})错误！", dateStr, format);
        }
        return date;
    }
}