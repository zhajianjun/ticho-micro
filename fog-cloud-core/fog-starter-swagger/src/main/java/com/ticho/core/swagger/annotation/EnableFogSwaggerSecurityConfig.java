package com.ticho.core.swagger.annotation;

import com.ticho.core.swagger.config.DefaultModelPropertySortPlugin;
import com.ticho.core.swagger.config.DefaultModelToSwaggerMapper;
import com.ticho.core.swagger.config.DefaultSwaggerParameterBuilder;
import com.ticho.core.swagger.config.SwaggerSecurityConfig;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author AdoroTutto
 * @date 2021-10-18 21:29
 */
// @formatter:off
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
    SwaggerSecurityConfig.class
})
// @formatter:on
public @interface EnableFogSwaggerSecurityConfig {
}
