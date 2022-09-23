package com.ticho.common.security.config;


import com.ticho.common.security.convert.JwtConverter;
import com.ticho.common.security.filter.JwtAuthenticationTokenFilter;
import com.ticho.common.security.filter.RewriteAccessDenyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtConverter jwtConverter;
    private final UserDetailsChecker userDetailsChecker;

    public WebSecurityConfig(UserDetailsService userDetailsService, JwtConverter jwtConverter, UserDetailsChecker userDetailsChecker){
        this.userDetailsService = userDetailsService;
        this.jwtConverter = jwtConverter;
        this.userDetailsChecker = userDetailsChecker;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder,
            PasswordEncoder passwordEncoder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers("/auth/**").permitAll()
            .antMatchers("/doc.html", "/swagger-resources/**", "/webjars/**", "/v2/api-docs","/favicon.ico", "/css/**", "/js/**", "/imgs/**").permitAll()
            .antMatchers("/druid/**").anonymous().anyRequest().authenticated()
            .and()
            .headers()
            .cacheControl();
        http.addFilterBefore(new JwtAuthenticationTokenFilter(jwtConverter, userDetailsChecker), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(new RewriteAccessDenyFilter());


        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        //让Spring security放行所有preflight request
        //registry.requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
    }

}