package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.MenuFuncRepository;
import com.ticho.upms.infrastructure.entity.MenuFunc;
import com.ticho.upms.infrastructure.mapper.MenuFuncMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 菜单功能关联关系 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class MenuFuncRepositoryImpl extends RootServiceImpl<MenuFuncMapper, MenuFunc> implements MenuFuncRepository {

    @Override
    public List<MenuFunc> listByMenuId(Long menuId) {
        if (Objects.isNull(menuId)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<MenuFunc> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MenuFunc::getMenuId, menuId);
        return list(wrapper);
    }

    @Override
    public boolean removeByMenuId(Long menuId) {
        if (Objects.isNull(menuId)) {
            return false;
        }
        LambdaQueryWrapper<MenuFunc> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MenuFunc::getMenuId, menuId);
        return remove(wrapper);
    }

    @Override
    public boolean removeByMenuIdAndFuncIds(Long menuId, Collection<Long> funcIds) {
        if (Objects.isNull(menuId) || CollUtil.isEmpty(funcIds)) {
            return false;
        }
        LambdaQueryWrapper<MenuFunc> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MenuFunc::getMenuId, menuId);
        wrapper.in(MenuFunc::getFuncId, funcIds);
        return remove(wrapper);
    }

}
