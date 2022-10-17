package com.ticho.upms.infrastructure.mapper;

import com.ticho.boot.datasource.mapper.RootMapper;
import com.ticho.upms.infrastructure.entity.RoleFunc;
import org.springframework.stereotype.Repository;

/**
 * 角色功能关联关系 mapper
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Repository
public interface RoleFuncMapper extends RootMapper<RoleFunc> {

}