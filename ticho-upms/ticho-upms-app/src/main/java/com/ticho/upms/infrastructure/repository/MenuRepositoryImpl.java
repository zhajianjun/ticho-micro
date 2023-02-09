package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.boot.json.util.JsonUtil;
import com.ticho.boot.redis.util.RedisUtil;
import com.ticho.upms.domain.repository.MenuRepository;
import com.ticho.upms.infrastructure.core.constant.RedisConst;
import com.ticho.upms.infrastructure.core.prop.CacheProperty;
import com.ticho.upms.infrastructure.entity.Menu;
import com.ticho.upms.infrastructure.mapper.MenuMapper;
import com.ticho.upms.interfaces.query.MenuQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
    private RedisUtil<String, String> redisUtil;

    @Autowired
    private CacheProperty cacheProperty;


    @Override
    public List<Menu> list() {
        // @formatter:off
        boolean exists = redisUtil.exists(RedisConst.MENU_LIST_KEY);
        if (exists) {
            String vGet = redisUtil.vGet(RedisConst.MENU_LIST_KEY);
            return JsonUtil.toList(vGet, Menu.class);
        }
        List<Menu> list = super.list();
        redisUtil.vSet(RedisConst.MENU_LIST_KEY, JsonUtil.toJsonString(list), cacheProperty.getMenuExpire(), TimeUnit.SECONDS);
        return list;
        // @formatter:on
    }


    @Override
    public boolean removeById(Serializable id) {
        boolean remove = super.removeById(id);
        if (remove) {
            redisUtil.delete(RedisConst.MENU_LIST_KEY);
        }
        return remove;
    }

    @Override
    public boolean updateById(Menu menu) {
        boolean update = super.updateById(menu);
        if (update) {
            redisUtil.delete(RedisConst.MENU_LIST_KEY);
        }
        return update;
    }


    @Override
    public List<Menu> list(MenuQuery query) {
        LambdaQueryWrapper<Menu> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Objects.nonNull(query.getId()), Menu::getId, query.getId());
        wrapper.eq(Objects.nonNull(query.getParentId()), Menu::getParentId, query.getParentId());
        wrapper.eq(StrUtil.isNotBlank(query.getStructure()), Menu::getStructure, query.getStructure());
        wrapper.eq(Objects.nonNull(query.getType()), Menu::getType, query.getType());
        wrapper.eq(StrUtil.isNotBlank(query.getName()), Menu::getName, query.getName());
        wrapper.eq(StrUtil.isNotBlank(query.getPath()), Menu::getPath, query.getPath());
        wrapper.eq(StrUtil.isNotBlank(query.getComponent()), Menu::getComponent, query.getComponent());
        wrapper.eq(StrUtil.isNotBlank(query.getRedirect()), Menu::getRedirect, query.getRedirect());
        wrapper.eq(Objects.nonNull(query.getExtFlag()), Menu::getExtFlag, query.getExtFlag());
        wrapper.eq(Objects.nonNull(query.getCacheAble()), Menu::getCacheAble, query.getCacheAble());
        wrapper.eq(Objects.nonNull(query.getInvisible()), Menu::getInvisible, query.getInvisible());
        wrapper.eq(Objects.nonNull(query.getClosable()), Menu::getClosable, query.getClosable());
        wrapper.eq(StrUtil.isNotBlank(query.getIcon()), Menu::getIcon, query.getIcon());
        wrapper.eq(Objects.nonNull(query.getSort()), Menu::getSort, query.getSort());
        wrapper.eq(Objects.nonNull(query.getStatus()), Menu::getStatus, query.getStatus());
        wrapper.eq(StrUtil.isNotBlank(query.getRemark()), Menu::getRemark, query.getRemark());
        wrapper.eq(Objects.nonNull(query.getVersion()), Menu::getVersion, query.getVersion());
        wrapper.eq(StrUtil.isNotBlank(query.getCreateBy()), Menu::getCreateBy, query.getCreateBy());
        wrapper.eq(Objects.nonNull(query.getCreateTime()), Menu::getCreateTime, query.getCreateTime());
        wrapper.eq(StrUtil.isNotBlank(query.getUpdateBy()), Menu::getUpdateBy, query.getUpdateBy());
        wrapper.eq(Objects.nonNull(query.getUpdateTime()), Menu::getUpdateTime, query.getUpdateTime());
        wrapper.eq(Objects.nonNull(query.getIsDelete()), Menu::getIsDelete, query.getIsDelete());
        wrapper.orderByAsc(Menu::getParentId);
        wrapper.orderByAsc(Menu::getSort);
        return list(wrapper);
    }

    @Override
    public List<Menu> listByIds(Collection<? extends Serializable> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return super.listByIds(ids);
    }

}
