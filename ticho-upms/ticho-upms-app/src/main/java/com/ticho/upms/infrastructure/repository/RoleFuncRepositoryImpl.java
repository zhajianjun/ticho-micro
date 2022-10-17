package com.ticho.upms.infrastructure.repository;

import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.RoleFuncRepository;
import com.ticho.upms.infrastructure.entity.RoleFunc;
import com.ticho.upms.infrastructure.mapper.RoleFuncMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色功能关联关系 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class RoleFuncRepositoryImpl extends RootServiceImpl<RoleFuncMapper, RoleFunc> implements RoleFuncRepository {

}
