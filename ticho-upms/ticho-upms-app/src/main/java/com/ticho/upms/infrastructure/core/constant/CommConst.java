package com.ticho.upms.infrastructure.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 通用静态变量
 * @author AdoroTutto
 * @date 2020-08-26 21:59
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommConst {


    public static final long DICT_PARENT_ID = -1;
    public static final Integer DICT_LEVEL_INIT_ID = 1;
    public static final Integer YES = 1;
    public static final Integer NO = 0;

    public static final String CLIENT_ID = "web";
    public static final String CLIENT_SECRET = "web";
    public static final String GRANT_TYPE = "password";
    public static final String SCOPE = "all";

    public static final String RESULT_VIEW = "com.ticho.commons.interceptor.ResultViewInterceptor";

    public static final String PERM_KEY = "perm";
}
