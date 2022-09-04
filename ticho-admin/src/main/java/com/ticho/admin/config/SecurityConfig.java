package com.ticho.admin.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * security 配置
 *
 * @author zhajianjun
 * @date 2022-09-02 17:45
 */
@Configuration(proxyBeanMethods = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AdminServerProperties adminServer;

    private final SecurityProperties security;


    public SecurityConfig(AdminServerProperties adminServer, SecurityProperties security) {
        this.adminServer = adminServer;
        this.security = security;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServer.path("/"));
        http.authorizeRequests(auth ->
                auth.antMatchers(this.adminServer.path("/assets/**")).permitAll()
                    .antMatchers(this.adminServer.path("/actuator/info")).permitAll()
                    .antMatchers(this.adminServer.path("/actuator/health")).permitAll()
                    .antMatchers(this.adminServer.path("/health")).permitAll()
                    .antMatchers(this.adminServer.path("/login")).permitAll().anyRequest().authenticated())
            .formLogin(formLogin ->
                formLogin.loginPage(this.adminServer.path("/login"))
                         .successHandler(successHandler))
            .logout(logout -> logout.logoutUrl(this.adminServer.path("/logout")))
            .httpBasic(Customizer.withDefaults())

            .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .ignoringRequestMatchers(
                new AntPathRequestMatcher(this.adminServer.path("/instances"), HttpMethod.POST.toString()),
                new AntPathRequestMatcher(this.adminServer.path("/instances/*"), HttpMethod.DELETE.toString()),
                new AntPathRequestMatcher(this.adminServer.path("/actuator/**")))
            );
        // @formatter:on
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth.inMemoryAuthentication()
            .withUser(security.getUser().getName())
            .password("{noop}" + security.getUser().getPassword())
            .roles("USER");
        // @formatter:on
    }


}
