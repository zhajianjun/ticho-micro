package com.ticho.upms.interfaces.api;

import com.ticho.boot.view.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 权限用户登录feign接口
 *
 * @author zhajianjun
 * @date 2022-09-22 14:49
 */
@FeignClient(value = "ticho-upms-app", contextId = "OauthService", path = "oauth")
public interface OauthProvider {


    /**
     * 公钥
     *
     * @return {@link Result}<{@link String}>
     */
    @GetMapping("publicKey")
    Result<String> publicKey();

}
