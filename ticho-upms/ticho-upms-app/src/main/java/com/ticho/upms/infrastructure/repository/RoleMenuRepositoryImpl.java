package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.upms.domain.repository.RoleMenuRepository;
import com.ticho.upms.infrastructure.entity.RoleMenu;
import com.ticho.upms.infrastructure.mapper.RoleMenuMapper;
import com.ticho.upms.interfaces.query.RoleMenuQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 角色菜单关联关系 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class RoleMenuRepositoryImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuRepository {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public boolean save(RoleMenu roleMenu) {
        if (roleMenu == null) {
            log.info("角色菜单关联关系保存异常，对象为null");
            return false;
        }
        return roleMenuMapper.insert(roleMenu) == 1;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<RoleMenu> roleMenus) {
        return this.saveBatch(roleMenus, 200);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<RoleMenu> roleMenus, int batchSize) {
        if (CollUtil.isEmpty(roleMenus)) {
            log.info("角色菜单关联关系批量保存异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<List<RoleMenu>> split = CollUtil.split(roleMenus, batchSize);
        Integer total = split.stream().map(roleMenuMapper::insertBatch).reduce(1, Integer::sum);
        return total == roleMenus.size();
    }

    @Override
    public boolean removeById(Serializable roleId) {
        if (roleId == null) {
            log.info("角色菜单关联关系删除异常，主键ID为null");
            return false;
        }
        return roleMenuMapper.deleteById(roleId) > 0;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> roleIds) {
        return removeByIds(roleIds, 200);
    }

    public boolean removeByIds(Collection<? extends Serializable> roleIds, int batchSize) {
        if (CollUtil.isEmpty(roleIds)) {
            log.info("角色菜单关联关系批量删除异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<? extends List<? extends Serializable>> split = CollUtil.split(roleIds, batchSize);
        Integer total = split.stream().map(roleMenuMapper::deleteBatchIds).reduce(1, Integer::sum);
        return total == roleIds.size();
    }

    @Override
    public boolean updateById(RoleMenu roleMenu) {
        if (roleMenu == null) {
            log.info("角色菜单关联关系更新异常，角色为null");
            return false;
        }
        return roleMenuMapper.updateById(roleMenu) > 0;
    }

    @Override
    public RoleMenu getById(Serializable roleId) {
        return roleMenuMapper.selectById(roleId);
    }

    @Override
    public List<RoleMenu> list(RoleMenuQuery query) {
        return roleMenuMapper.selectByConditions(query);
    }

}
