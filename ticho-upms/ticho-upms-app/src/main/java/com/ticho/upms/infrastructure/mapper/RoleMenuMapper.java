package com.ticho.upms.infrastructure.mapper;

import com.ticho.boot.datasource.mapper.RootMapper;
import com.ticho.upms.infrastructure.entity.RoleMenu;
import org.springframework.stereotype.Repository;

/**
 * 角色菜单关联关系 mapper
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Repository
public interface RoleMenuMapper extends RootMapper<RoleMenu> {

}