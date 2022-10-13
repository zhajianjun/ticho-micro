package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.upms.domain.repository.MenuFuncRepository;
import com.ticho.upms.infrastructure.entity.MenuFunc;
import com.ticho.upms.infrastructure.mapper.MenuFuncMapper;
import com.ticho.upms.interfaces.query.MenuFuncQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 菜单功能关联关系 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class MenuFuncRepositoryImpl extends ServiceImpl<MenuFuncMapper, MenuFunc> implements MenuFuncRepository {

    @Autowired
    private MenuFuncMapper menuFuncMapper;

    @Override
    public boolean save(MenuFunc menuFunc) {
        if (menuFunc == null) {
            log.info("菜单功能关联关系保存异常，对象为null");
            return false;
        }
        return menuFuncMapper.insert(menuFunc) == 1;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<MenuFunc> menuFuncs) {
        return this.saveBatch(menuFuncs, 200);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<MenuFunc> menuFuncs, int batchSize) {
        if (CollUtil.isEmpty(menuFuncs)) {
            log.info("菜单功能关联关系批量保存异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<List<MenuFunc>> split = CollUtil.split(menuFuncs, batchSize);
        Integer total = split.stream().map(menuFuncMapper::insertBatch).reduce(1, Integer::sum);
        return total == menuFuncs.size();
    }

    @Override
    public boolean removeById(Serializable menuId) {
        if (menuId == null) {
            log.info("菜单功能关联关系删除异常，主键ID为null");
            return false;
        }
        return menuFuncMapper.deleteById(menuId) > 0;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> menuIds) {
        return removeByIds(menuIds, 200);
    }

    public boolean removeByIds(Collection<? extends Serializable> menuIds, int batchSize) {
        if (CollUtil.isEmpty(menuIds)) {
            log.info("菜单功能关联关系批量删除异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<? extends List<? extends Serializable>> split = CollUtil.split(menuIds, batchSize);
        Integer total = split.stream().map(menuFuncMapper::deleteBatchIds).reduce(1, Integer::sum);
        return total == menuIds.size();
    }

    @Override
    public boolean updateById(MenuFunc menuFunc) {
        if (menuFunc == null) {
            log.info("菜单功能关联关系更新异常，角色为null");
            return false;
        }
        return menuFuncMapper.updateById(menuFunc) > 0;
    }

    @Override
    public MenuFunc getById(Serializable menuId) {
        return menuFuncMapper.selectById(menuId);
    }

    @Override
    public List<MenuFunc> list(MenuFuncQuery query) {
        return menuFuncMapper.selectByConditions(query);
    }

}
