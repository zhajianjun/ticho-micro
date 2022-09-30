package com.ticho.upms.interfaces.api;

import com.ticho.boot.view.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 14:49
 */
@FeignClient(value = "ticho-upms-app", contextId = "OauthService", path = "oauth")
public interface OauthInterface {

    /**
     * 获取公钥
     *
     * @return 公钥信息
     */
    @GetMapping("publicKey")
    Result<String> publicKey();

}
