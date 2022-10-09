package com.ticho.upms.interfaces.api;

import com.ticho.boot.view.core.Result;
import com.ticho.upms.interfaces.dto.UpmsUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户信息feign接口
 *
 * @author zhajianjun
 * @date 2022-09-22 14:49
 */
@FeignClient(value = "ticho-upms-app", contextId = "OauthService", path = "oauth")
public interface UserProvider {


    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return {@link Result}<{@link UpmsUserDTO}>
     */
    @GetMapping("getByUsername")
    Result<UpmsUserDTO> getByUsername(@RequestParam("username") String username);

}

