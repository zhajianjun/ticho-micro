package com.ticho.upms.infrastructure.repository;

import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.UserRoleRepository;
import com.ticho.upms.infrastructure.entity.UserRole;
import com.ticho.upms.infrastructure.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户角色关联关系 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class UserRoleRepositoryImpl extends RootServiceImpl<UserRoleMapper, UserRole> implements UserRoleRepository {

}
