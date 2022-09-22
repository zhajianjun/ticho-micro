package com.ticho.common.security.constant;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 11:07
 */
public class SecurityConst {
    private SecurityConst() {
    }

    /** 加载用户类型:用户密码 */
    public static final String LOAD_USER_TYPE_USERNAME = "load_user_type:username";
    /** 加载用户类型:手机号码 */
    public static final String LOAD_USER_TYPE_PHONE = "load_user_type:phone";
    /** 加载用户类型:邮箱 */
    public static final String LOAD_USER_TYPE_EMAIL = "load_user_type:email";


    /** 登录方式:用户密码 */
    public static final String LOGIN_USER_TYPE_USERNAME = "login_user_type:username";
    /** 登录方式:手机号码 */
    public static final String LOGIN_USER_TYPE_PHONE = "login_user_type:phone";
    /** 登录方式:邮箱 */
    public static final String LOGIN_USER_TYPE_EMAIL = "login_user_type:email";


}
