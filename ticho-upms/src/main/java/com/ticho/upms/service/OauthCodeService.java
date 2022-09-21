package com.ticho.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.entity.OauthCode;

/**
 * oauth2授权码信息
 *
 * @author zhajianjun
 * @date 2022-09-07 16:48
 */
public interface OauthCodeService extends IService<OauthCode> {

    /**
     * 根据客户端id查询,并存储线程中
     *
     * @param code 授权码
     * @return oauth2授权码信息
     */
    OauthCode getByCode(String code);

}
