package com.ticho.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 包装视图注解
 *
 * @author AdoroTutto
 * @date 2021-10-16 20:24
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface View {
    /**
     * 是否忽略，只对action对应的方法有效
     */
    boolean ignore() default false;
}
