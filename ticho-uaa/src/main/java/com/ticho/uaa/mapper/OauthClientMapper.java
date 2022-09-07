package com.ticho.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ticho.uaa.entity.OauthClient;
import org.springframework.stereotype.Repository;

/**
 * oauth2客户端信息 mapper
 *
 * @author zhajianjun
 * @date 2021-10-30 23:30
 */
@Repository
public interface OauthClientMapper extends BaseMapper<OauthClient> {


}