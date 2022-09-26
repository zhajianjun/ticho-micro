package com.ticho.upms.api;

import com.ticho.boot.view.core.Result;
import com.ticho.upms.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 14:49
 */
@FeignClient(value = "ticho-upms-app", contextId = "UserService", path = "user")
public interface UserBizFeignService {


    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("getByUsername")
    Result<UserDTO> getByUsername(@RequestParam("username") String username);

}
