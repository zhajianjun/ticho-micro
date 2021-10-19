package com.ticho.core.datasource.config;

import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.ticho.core.mvc.factory.YamlPropertySourceFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author AdoroTutto
 * @date 2021-10-17 22:52
 */
@Configuration
@EnableTransactionManagement
@Order(Ordered.HIGHEST_PRECEDENCE)
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:fog-cloud-mybatis-plus.yml")
@MapperScan("com.ticho.**.mapper")
public class MybatisPlusConfig {

    @Value("${mybatis-plus.maxLimit:1000}")
    private long maxLimit;

    /**
     * mybatis-plus 乐观锁拦截器
     */
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }
}
