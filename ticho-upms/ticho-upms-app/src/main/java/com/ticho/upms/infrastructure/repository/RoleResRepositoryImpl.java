package com.ticho.upms.infrastructure.repository;

import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.RoleResRepository;
import com.ticho.upms.infrastructure.entity.RoleRes;
import com.ticho.upms.infrastructure.mapper.RoleResMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色和资源关联表 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class RoleResRepositoryImpl extends RootServiceImpl<RoleResMapper, RoleRes> implements RoleResRepository {

}
