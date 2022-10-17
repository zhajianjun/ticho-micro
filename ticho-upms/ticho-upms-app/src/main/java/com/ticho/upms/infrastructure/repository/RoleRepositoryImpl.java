package com.ticho.upms.infrastructure.repository;

import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.RoleRepository;
import com.ticho.upms.infrastructure.entity.Role;
import com.ticho.upms.infrastructure.mapper.RoleMapper;
import com.ticho.upms.interfaces.query.RoleQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色信息 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class RoleRepositoryImpl extends RootServiceImpl<RoleMapper, Role> implements RoleRepository {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> list(RoleQuery query) {
        return roleMapper.selectByConditions(query);
    }

}
