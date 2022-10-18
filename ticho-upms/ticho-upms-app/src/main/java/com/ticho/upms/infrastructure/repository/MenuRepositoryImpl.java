package com.ticho.upms.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.MenuRepository;
import com.ticho.upms.infrastructure.entity.Menu;
import com.ticho.upms.infrastructure.mapper.MenuMapper;
import com.ticho.upms.interfaces.query.MenuQuery;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public List<Menu> list(MenuQuery query) {
        LambdaQueryWrapper<Menu> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Menu::getId, query.getId());
        wrapper.eq(Menu::getParentId, query.getParentId());
        wrapper.eq(Menu::getStructure, query.getStructure());
        wrapper.eq(Menu::getType, query.getType());
        wrapper.eq(Menu::getName, query.getName());
        wrapper.eq(Menu::getPath, query.getPath());
        wrapper.eq(Menu::getComponent, query.getComponent());
        wrapper.eq(Menu::getRedirect, query.getRedirect());
        wrapper.eq(Menu::getExtFlag, query.getExtFlag());
        wrapper.eq(Menu::getCacheAble, query.getCacheAble());
        wrapper.eq(Menu::getInvisible, query.getInvisible());
        wrapper.eq(Menu::getClosable, query.getClosable());
        wrapper.eq(Menu::getIcon, query.getIcon());
        wrapper.eq(Menu::getSort, query.getSort());
        wrapper.eq(Menu::getStatus, query.getStatus());
        wrapper.eq(Menu::getRemark, query.getRemark());
        wrapper.eq(Menu::getVersion, query.getVersion());
        wrapper.eq(Menu::getCreateBy, query.getCreateBy());
        wrapper.eq(Menu::getCreateTime, query.getCreateTime());
        wrapper.eq(Menu::getUpdateBy, query.getUpdateBy());
        wrapper.eq(Menu::getUpdateTime, query.getUpdateTime());
        wrapper.eq(Menu::getIsDelete, query.getIsDelete());
        return list(wrapper);
    }

}
