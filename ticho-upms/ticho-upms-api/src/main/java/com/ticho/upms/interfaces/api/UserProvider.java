package com.ticho.upms.interfaces.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 用户信息feign接口
 *
 * @author zhajianjun
 * @date 2022-09-22 14:49
 */
@FeignClient(value = "ticho-upms", contextId = "UserService", path = "oauth")
public interface UserProvider {

}

