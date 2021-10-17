package com.ticho.mvc.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Json工具
 *
 * @author AdoroTutto
 * @date 2021-10-16 20:24
 */
public class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String EMPTY = "";


    /**
     * Object 转换 json格式的String
     * @param obj Object
     * @return String
     */
    public static String toJsonString(Object obj) {
        try {
            if(obj instanceof String) {
                return obj.toString();
            }
            return Objects.nonNull(obj) ? MAPPER.writeValueAsString(obj) : EMPTY;
        } catch (Exception e) {
            log.error("toJSONString exception {}", obj.toString(), e);
            return EMPTY;
        }
    }

    /**
     * json格式的String 转换成对象
     * @param jsonString Object
     * @param clazz 该对象的类
     * @return T
     */
    public static <T> T toJavaObject(String jsonString, Class<T> clazz) {
        try {
            Optional.ofNullable(clazz).orElseThrow(NullPointerException::new);
            return StringUtils.isEmpty(jsonString) ? null : MAPPER.readValue(jsonString, clazz);
        } catch (Exception e) {
            log.error("tojavaObject exception {}", jsonString, e);
            return null;
        }
    }

    /**
     * json格式的String 转换成集合
     * @param jsonString Object
     * @return T
     */
    public static List<Object> toList(String jsonString) {
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, Object.class);
            return StringUtils.isEmpty(jsonString) ? Collections.emptyList() : MAPPER.readValue(jsonString, javaType);
        } catch (Exception e) {
            log.error("toList exception {}", jsonString, e);
            return Collections.emptyList();
        }
    }

    /**
     * json格式的String 转换成集合,带泛型
     * @param jsonString Object
     * @param clazz 集合的泛型对象类
     * @return List<T>
     */
    public static <T> List<T> toList(String jsonString, Class<T> clazz) {
        try {
            Optional.ofNullable(clazz).orElseThrow(NullPointerException::new);
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
            return StringUtils.isEmpty(jsonString) ? Collections.emptyList() : MAPPER.readValue(jsonString, javaType);
        } catch (Exception e) {
            log.error("toList exception {}", jsonString, e);
            return Collections.emptyList();
        }
    }

    /**
     * json格式的String 转换成集合
     * @param jsonString Object
     * @return Map<Object, Object>
     */
    public static Map<Object, Object> toMap(String jsonString) {
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(Map.class, Object.class, Object.class);
            return StringUtils.isEmpty(jsonString) ? new LinkedHashMap<>() : MAPPER.readValue(jsonString, javaType);
        } catch (Exception e) {
            log.error("toMap exception {}", jsonString, e);
            return new LinkedHashMap<>();
        }
    }

    /**
     * json格式的String 转换成集合,带泛型
     * @param jsonString Object
     * @param kClass Map K 的泛型对象类
     * @param vClass Map V 的泛型对象类
     * @return Map<K, V>
     */
    public static <K, V> Map<K, V> toMap(String jsonString, Class<K> kClass, Class<V> vClass) {
        try {
            Optional.ofNullable(kClass).orElseThrow(NullPointerException::new);
            Optional.ofNullable(vClass).orElseThrow(NullPointerException::new);
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(Map.class, kClass, vClass);
            return StringUtils.isEmpty(jsonString) ? new LinkedHashMap<>() : MAPPER.readValue(jsonString, javaType);
        } catch (Exception e) {
            log.error("toMap exception {}", jsonString, e);
            return new LinkedHashMap<>();
        }
    }

    /**
     * 对象转Map
     * @param obj Object
     * @return Map<String, Object>
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(Object obj) {
        try {
            if (obj instanceof Map) {
                return (Map<String, Object>) obj;
            }
            String jsonString = toJsonString(obj);
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(Map.class, String.class, Object.class);
            return StringUtils.isEmpty(jsonString) ? new LinkedHashMap<>() : MAPPER.readValue(jsonString, javaType);
        } catch (Exception e) {
            log.error("toMap exception {}", obj, e);
            return new LinkedHashMap<>();
        }
    }

    public static <T> T jsonCopy(T obj, Class<T> clazz) {
        return obj != null ? toJavaObject(toJsonString(obj), clazz) : null;
    }

    /**
     * 得有getter和setter
     */
    public static void convertObj(Object source, Object target) {
        if (target == null || source == null || source == target) {
            return;
        }
        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
        // Copy操作
        copier.copy(source, target, null);
    }

    public static <T> List<T> convertList(List<T> sourceList, Class<T> clazz) {
        if (CollectionUtils.isEmpty(sourceList) || clazz == null) {
            return Collections.emptyList();
        }
        List<T> resultList = new ArrayList<>(sourceList.size());
        for (T item : sourceList) {
            T t;
            try {
                t = clazz.newInstance();
                convertObj(item, t);
                resultList.add(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resultList;
    }

    static {
        // 反序列化 默认遇到未知属性去时会抛一个JsonMappingException,所以关闭
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        /* 这个特性决定parser是否将允许使用非双引号属性名字， （这种形式在Javascript中被允许，但是JSON标准说明书中没有）。
         * 注意：由于JSON标准上需要为属性名称使用双引号，所以这也是一个非标准特性，默认是false的。
         * 同样，需要设置JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES为true，打开该特性。
         */
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        /*
         * 这个特性决定parser是否将允许使用非双引号属性名字， （这种形式在Javascript中被允许，但是JSON标准说明书中没有）。
         * 注意：由于JSON标准上需要为属性名称使用双引号，所以这也是一个非标准特性，默认是false的。
         * 同样，需要设置JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES为true，打开该特性。
         */
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
