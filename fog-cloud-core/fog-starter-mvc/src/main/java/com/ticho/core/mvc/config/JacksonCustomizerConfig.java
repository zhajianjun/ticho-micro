package com.ticho.core.mvc.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.ticho.core.mvc.converter.CustomLocalDateDeserializer;
import com.ticho.core.mvc.converter.CustomLocalDateTimeDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * RequestBody格式化
 *
 * @author AdoroTutto
 * @date 2021-10-27 0:08
 */
@Configuration
public class JacksonCustomizerConfig {
    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String localDateTimePattern;

    @Value("${spring.jackson.local-date-format:yyyy-MM-dd}")
    private String localDatePattern;

    @Value("${spring.jackson.local-time-format:HH:mm:ss}")
    private String localTimePattern;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        // @formatter:off
        return builder -> {
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(localDateTimePattern)));
            builder.deserializerByType(LocalDateTime.class, new CustomLocalDateTimeDeserializer(DateTimeFormatter.ofPattern(localDateTimePattern)));

            builder.serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(localDatePattern)));
            builder.deserializerByType(LocalDate.class, new CustomLocalDateDeserializer(DateTimeFormatter.ofPattern(localDatePattern)));

            builder.serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(localTimePattern)));
            builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(localTimePattern)));

            /*
             * Jackson全局转化long类型为String,解决jackson序列化时传入前端Long类型缺失精度问题
             */
            builder.serializerByType(BigInteger.class, ToStringSerializer.instance);
            builder.serializerByType(BigDecimal.class, ToStringSerializer.instance);
            builder.serializerByType(Long.class, ToStringSerializer.instance);
            // FIXME 可能需要注释掉下面
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
        };
        // @formatter:on
    }
}
