package com.ticho.upms.infrastructure.repository;

import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.RoleMenuRepository;
import com.ticho.upms.infrastructure.entity.RoleMenu;
import com.ticho.upms.infrastructure.mapper.RoleMenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色菜单关联关系 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class RoleMenuRepositoryImpl extends RootServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuRepository {

}
