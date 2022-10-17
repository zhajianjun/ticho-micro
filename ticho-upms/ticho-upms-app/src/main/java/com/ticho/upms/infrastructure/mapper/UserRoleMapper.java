package com.ticho.upms.infrastructure.mapper;

import com.ticho.boot.datasource.mapper.RootMapper;
import com.ticho.upms.infrastructure.entity.UserRole;
import org.springframework.stereotype.Repository;

/**
 * 用户角色关联关系 mapper
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Repository
public interface UserRoleMapper extends RootMapper<UserRole> {


}