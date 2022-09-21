package com.ticho.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.upms.entity.OauthClient;
import com.ticho.upms.mapper.OauthClientMapper;
import com.ticho.upms.service.OauthClientService;
import org.springframework.stereotype.Service;

/**
 * oauth2客户端信息 实现
 *
 * @author zhajianjun
 * @date 2022-09-07 16:16
 */
@Service
public class OauthClientServiceImpl extends ServiceImpl<OauthClientMapper, OauthClient> implements OauthClientService {


    @Override
    public OauthClient getByClientId(String clientId) {
        return getById(clientId);
    }

}
