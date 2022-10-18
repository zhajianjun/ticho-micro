package com.ticho.upms.infrastructure.mapper;

import com.ticho.boot.datasource.mapper.RootMapper;
import com.ticho.upms.infrastructure.entity.RoleRes;
import org.springframework.stereotype.Repository;

/**
 * 角色和资源关联表 mapper
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Repository
public interface RoleResMapper extends RootMapper<RoleRes> {

}