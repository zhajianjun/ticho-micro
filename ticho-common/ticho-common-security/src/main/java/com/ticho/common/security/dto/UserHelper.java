package com.ticho.common.security.dto;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-26 10:32
 */
public interface UserHelper {

    String getTenantId();

    void setTenantId(String tenantId);

    String getUsername();

    void setUsername(String username);
}
