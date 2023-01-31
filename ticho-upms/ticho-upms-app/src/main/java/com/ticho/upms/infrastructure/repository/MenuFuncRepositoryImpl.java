package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.MenuFuncRepository;
import com.ticho.upms.infrastructure.entity.Func;
import com.ticho.upms.infrastructure.entity.MenuFunc;
import com.ticho.upms.infrastructure.mapper.FuncMapper;
import com.ticho.upms.infrastructure.mapper.MenuFuncMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 菜单功能关联关系 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class MenuFuncRepositoryImpl extends RootServiceImpl<MenuFuncMapper, MenuFunc> implements MenuFuncRepository {

    @Autowired
    private FuncMapper funcMapper;

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
    public Map<Long, List<Func>> getMenuFuncMap() {
        return getMenuFuncMap(list());
    }

    @Override
    public Map<Long, List<Func>> getMenuFuncMapByMenuIds(List<Long> menuIds) {
        if (CollUtil.isEmpty(menuIds)) {
            return Collections.emptyMap();
        }
        // 查询关联关系
        LambdaQueryWrapper<MenuFunc> wrapper = Wrappers.lambdaQuery();
        wrapper.in(MenuFunc::getMenuId, menuIds);
        List<MenuFunc> list = list(wrapper);
        return getMenuFuncMap(list);
    }

    private Map<Long, List<Func>> getMenuFuncMap(List<MenuFunc> menuFuncs) {
        // @formatter:off
        if (menuFuncs.isEmpty()) {
            return Collections.emptyMap();
        }
        // 提取关联关系所有的功能号id
        List<Long> funcIds = menuFuncs.stream().map(MenuFunc::getFuncId).collect(Collectors.toList());
        // 查询所有功能号信息，并转为key为id的map
        List<Func> funcs = funcMapper.selectBatchIds(funcIds);
        Map<Long, Func> funcMap = funcs
            .stream()
            .collect(Collectors.toMap(Func::getId, Function.identity()));
        // 根据关联关系进行分组，并根据功能号map提取功能号信息替换关联关系的功能号id
        return menuFuncs
            .stream()
            .collect(Collectors.groupingBy(MenuFunc::getMenuId,
                Collectors.mapping(x-> getFuncDTO(funcMap, x), Collectors.toList()))
            );
        // @formatter:on
    }

    private Func getFuncDTO(Map<Long, Func> funcMap, MenuFunc menuFunc) {
        Long funcId = menuFunc.getFuncId();
        return funcMap.get(funcId);
    }

    @Override
    public boolean removeByMenuIds(Collection<Long> menuIds) {
        if (CollUtil.isEmpty(menuIds)) {
            return false;
        }
        LambdaQueryWrapper<MenuFunc> wrapper = Wrappers.lambdaQuery();
        wrapper.in(MenuFunc::getMenuId, menuIds);
        return remove(wrapper);
    }

    @Override
    public boolean removeByFuncIds(Collection<Long> funcIds) {
        if (CollUtil.isEmpty(funcIds)) {
            return false;
        }
        LambdaQueryWrapper<MenuFunc> wrapper = Wrappers.lambdaQuery();
        wrapper.in(MenuFunc::getFuncId, funcIds);
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
