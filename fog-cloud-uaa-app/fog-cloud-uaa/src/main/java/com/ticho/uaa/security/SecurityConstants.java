package com.ticho.uaa.security;

import cn.hutool.core.codec.Base64;
import com.ticho.utils.JsonUtils;

/**
 *
 * @author AdoroTutto
 * @date 2020-07-05 0:36
 */
public class SecurityConstants {

    /**
     * 登录类型
     */
    public static final String DEFAULT_TYPE = "Default";
    public static final String IMAGE_CODE_TYPE = "ImageCode";

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "bearer";

    /**
     *
     */
    public enum HeaderKeyValue {
        /**
         * 全局过滤后，放入header中的标志k，v
         */
        GlobalFiltingFlag("global release flag", "any else");

        private final String key;
        private final String value;

        HeaderKeyValue(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
                + ".eyJhdWQiOlsicmVzb3VyY2UiXSwiY3JlYXRlVGltZSI6IjIwMjEtMDktMTEgMDE6MzU6MjgiLCJ1c2VyX25hbWUiOiJ6aGFqaWFuanVuIiwiYXV0aG9yIjoiQWRvcm9UdXR0byIsInNjb3BlIjpbImFsbCJdLCJleHAiOjE2MzEyOTcxMjgsImF1dGhvcml0aWVzIjpbInJvbGUxIiwicm9sZTIiLCJyb2xlMyJdLCJqdGkiOiJjYWRhOWI3YS04Y2U5LTRjNzctOTA0Ni04YWViYWQyMWE0ZTIiLCJjbGllbnRfaWQiOiJ3ZWIifQ.p6Ip432RPDc14SD0eYVN_tkOlsjhYvwxyQJy7Uwzn9g";
        String decodeStr = Base64.decodeStr(token);
        System.out.println(decodeStr);
        System.out.println(JsonUtils.toMap(decodeStr));
    }
}
