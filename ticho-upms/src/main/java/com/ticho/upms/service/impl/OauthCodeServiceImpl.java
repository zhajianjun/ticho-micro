package com.ticho.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.upms.entity.OauthCode;
import com.ticho.upms.mapper.OauthCodeMapper;
import com.ticho.upms.service.OauthCodeService;
import org.springframework.stereotype.Service;

/**
 * oauth2授权码信息 实现
 *
 * @author zhajianjun
 * @date 2022-09-07 16:50
 */
@Service
public class OauthCodeServiceImpl extends ServiceImpl<OauthCodeMapper, OauthCode> implements OauthCodeService {


    @Override
    public OauthCode getByCode(String code) {
        return getById(code);
    }

}
