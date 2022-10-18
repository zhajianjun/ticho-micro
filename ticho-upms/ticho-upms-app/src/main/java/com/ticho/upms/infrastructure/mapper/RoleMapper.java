package com.ticho.upms.infrastructure.mapper;

import com.ticho.boot.datasource.mapper.RootMapper;
import com.ticho.upms.infrastructure.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * 角色信息 mapper
 *
 * @author zhajianjun
 * @date 2022-10-18 09:08
 */
@Repository
public interface RoleMapper extends RootMapper<Role> {

}