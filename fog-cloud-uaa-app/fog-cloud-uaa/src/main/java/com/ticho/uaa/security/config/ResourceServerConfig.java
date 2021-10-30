package com.ticho.uaa.security.config;

import com.ticho.uaa.security.CustomAccessDecisionManager;
import com.ticho.uaa.security.SecurityConst;
import com.ticho.uaa.security.filter.GlobalFilter;
import com.ticho.uaa.security.view.AuthenticationFailView;
import com.ticho.uaa.security.view.NoAuthenticationMessageView;
import com.ticho.uaa.security.view.PermissionDeniedView;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * 资源服务器
 *
 * @author AdoroTutto
 * @date 2020-07-02 12:36
 */
@EnableResourceServer
@Configuration
@Order(3)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // @formatter:off
        resources
            // stateless  标记以指示在这些资源上仅允许基于令牌的身份验证
            .resourceId("resource").stateless(true)
            // Token失效返回视图
            .authenticationEntryPoint(new AuthenticationFailView())
            // 认证成功,权限不足返回视图
            .accessDeniedHandler(new PermissionDeniedView());
        // @formatter:on
    }

    /**
     * 用户资源访问权限
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            // 关闭csrf保护(关闭跨域保护)
            .csrf().disable()
            .formLogin().loginPage("/login.html").loginProcessingUrl("/login")
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            .and()
            .authorizeRequests()
            .antMatchers(SecurityConst.RELEASE_URL).permitAll()
            // 给特定资源接口放行
            .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                @Override
                public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                    // 权限判断
                    o.setAccessDecisionManager(new CustomAccessDecisionManager());
                    return o;
                }
            })
            // 其它接口则需要认证
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            // 无Authorization相关header参数
            .authenticationEntryPoint(new NoAuthenticationMessageView())
            .and()
            // 在认证处理器之前添加过滤器
            .addFilterBefore(new GlobalFilter(SecurityConst.RELEASE_URL), AbstractPreAuthenticatedProcessingFilter.class)
            // 解决iframe无法访问
            .headers().frameOptions().sameOrigin();
        // @formatter:on
    }
}
