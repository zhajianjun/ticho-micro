package com.ticho.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ticho.uaa.entity.OauthClientDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * oauth2客户端信息 mapper
 *
 * @author zhajianjun
 * @date 2021-10-30 23:30
 */
@Repository
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {

    /**
     * 根据条件查询 oauth2客户端信息 列表
     *
     * @param oauthClientDetails 条件
     * @return List<OauthClientDetails> oauth2客户端信息 列表
     */
    List<OauthClientDetails> selectByConditions(OauthClientDetails oauthClientDetails);

}