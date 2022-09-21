package com.ticho.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.entity.OauthClient;

/**
 * 获取oauth2客户端信息
 *
 * @author zhajianjun
 * @date 2022-09-07 16:11
 */
public interface OauthClientService extends IService<OauthClient> {

    /**
     * 根据客户端id查询,并存储线程中
     *
     * @param clientId 客户端id
     * @return oauth2客户端信息
     */
    OauthClient getByClientId(String clientId);

}
