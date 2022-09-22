package com.ticho.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ticho.upms.entity.OauthCode;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * oauth2授权码信息 mapper
 *
 * @author zhajianjun
 * @date 2021-10-30 23:30
 */
@Repository
public interface OauthCodeMapper extends BaseMapper<OauthCode> {

    /**
     * 根据条件查询 oauth2授权码信息 列表
     *
     * @param oauthCode 条件
     * @return List<OauthCode> oauth2授权码信息 列表
     */
    List<OauthCode> selectByConditions(OauthCode oauthCode);

}