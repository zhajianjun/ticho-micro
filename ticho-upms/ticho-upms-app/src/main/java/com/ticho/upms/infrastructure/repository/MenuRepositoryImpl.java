package com.ticho.upms.infrastructure.repository;

import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.MenuRepository;
import com.ticho.upms.infrastructure.entity.Menu;
import com.ticho.upms.infrastructure.mapper.MenuMapper;
import com.ticho.upms.interfaces.query.MenuQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单信息 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class MenuRepositoryImpl extends RootServiceImpl<MenuMapper, Menu> implements MenuRepository {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> list(MenuQuery query) {
        return menuMapper.selectByConditions(query);
    }

}
