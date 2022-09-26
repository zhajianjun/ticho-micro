package com.ticho.auth.api;

import com.ticho.boot.view.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 14:49
 */
@FeignClient(value = "ticho-auth-app", contextId = "AuthService", path = "oauth")
public interface AuthBizFeignService {


    /**
     * 获取公钥
     *
     * @return 公钥信息
     */
    @GetMapping("publicKey")
    Result<String> publicKey();

}
