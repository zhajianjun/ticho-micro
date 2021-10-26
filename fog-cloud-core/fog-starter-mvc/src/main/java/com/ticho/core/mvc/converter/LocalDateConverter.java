package com.ticho.core.mvc.converter;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * LocalDate转换
 * <p>
 *     GET请求，Query查询Date时间类型参数转换
 * </p>
 * @author AdoroTutto
 * @date 2021-10-27 0:02
 */
@Component
public class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(@NonNull String source) {
        if (StrUtil.isBlank(source)) {
            return null;
        }
        if (source.matches(ConvertConsant.YYYY_MM_DD_REGEX)) {
            return LocalDate.parse(source, DateTimeFormatter.ofPattern(ConvertConsant.YYYY_MM_DD));
        }
        throw new IllegalArgumentException("Invalid value '" + source + "'");
    }
}