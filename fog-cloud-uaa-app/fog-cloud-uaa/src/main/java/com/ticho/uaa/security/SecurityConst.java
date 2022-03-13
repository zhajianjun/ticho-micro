package com.ticho.uaa.security;

import cn.hutool.core.codec.Base64;
import com.ticho.core.mvc.util.JsonUtils;

/**
 *
 * @author AdoroTutto
 * @date 2020-07-05 0:36
 */
public class SecurityConst {

    /**
     * 登录类型
     */
    public static final String DEFAULT_TYPE = "Default";
    public static final String IMAGE_CODE_TYPE = "ImageCode";

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "bearer";
    public static final String PERMIT_ALL = "permitAll";

    /**
     * 放行的url，可以放到全局配置里,必须/开头
     */
    public static final String[] RELEASE_URL = {"/doc.html", "/swagger-resources/**", "/webjars/**", "/v2/api-docs",
            "/favicon.ico", "/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.ttf", "/**/*.woff", "/**/oauth/**", "/login",
            "/login.html"};

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
                + ".eyJhdWQiOlsicmVzb3VyY2UiXSwiY3JlYXRlVGltZSI6IjIwMjEtMDktMTEgMDE6MzU6MjgiLCJ1c2VyX25hbWUiOiJ6aGFqaWFuanVuIiwiYXV0aG9yIjoiQWRvcm9UdXR0byIsInNjb3BlIjpbImFsbCJdLCJleHAiOjE2MzEyOTcxMjgsImF1dGhvcml0aWVzIjpbInJvbGUxIiwicm9sZTIiLCJyb2xlMyJdLCJqdGkiOiJjYWRhOWI3YS04Y2U5LTRjNzctOTA0Ni04YWViYWQyMWE0ZTIiLCJjbGllbnRfaWQiOiJ3ZWIifQ.p6Ip432RPDc14SD0eYVN_tkOlsjhYvwxyQJy7Uwzn9g";
        String decodeStr = Base64.decodeStr(token);
        System.out.println(decodeStr);
        System.out.println(JsonUtils.toMap(decodeStr));
    }
}
