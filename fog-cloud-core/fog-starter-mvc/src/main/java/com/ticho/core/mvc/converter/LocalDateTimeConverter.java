package com.ticho.core.mvc.converter;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime转换
 * <p>
 *     GET请求，Query查询Date时间类型参数转换
 * </p>
 * @author AdoroTutto
 * @date 2021-10-27 0:01
 */
@Component
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(@NonNull String source) {
        if (StrUtil.isBlank(source)) {
            return null;
        }
        if (source.matches(ConvertConsant.YYYY_MM_DD_REGEX)) {
            LocalDate localDate = LocalDate.parse(source, DateTimeFormatter.ofPattern(ConvertConsant.YYYY_MM_DD));
            return LocalDateTime.of(localDate, LocalTime.MIN);
        }
        if (source.matches(ConvertConsant.YYYY_MM_DD_HH_MM_SS_REGEX)) {
            return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(ConvertConsant.YYYY_MM_DD_HH_MM_SS));
        }
        throw new IllegalArgumentException("Invalid value '" + source + "'");
    }
}
