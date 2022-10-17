package com.ticho.upms.infrastructure.repository;

import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.MenuFuncRepository;
import com.ticho.upms.infrastructure.entity.MenuFunc;
import com.ticho.upms.infrastructure.mapper.MenuFuncMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 菜单功能关联关系 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class MenuFuncRepositoryImpl extends RootServiceImpl<MenuFuncMapper, MenuFunc> implements MenuFuncRepository {

}
