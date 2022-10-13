package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.upms.domain.repository.MenuRepository;
import com.ticho.upms.infrastructure.entity.Menu;
import com.ticho.upms.infrastructure.mapper.MenuMapper;
import com.ticho.upms.interfaces.query.MenuQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 菜单信息 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class MenuRepositoryImpl extends ServiceImpl<MenuMapper, Menu> implements MenuRepository {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public boolean save(Menu menu) {
        if (menu == null) {
            log.info("菜单信息保存异常，对象为null");
            return false;
        }
        return menuMapper.insert(menu) == 1;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<Menu> menus) {
        return this.saveBatch(menus, 200);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<Menu> menus, int batchSize) {
        if (CollUtil.isEmpty(menus)) {
            log.info("菜单信息批量保存异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<List<Menu>> split = CollUtil.split(menus, batchSize);
        Integer total = split.stream().map(menuMapper::insertBatch).reduce(1, Integer::sum);
        return total == menus.size();
    }

    @Override
    public boolean removeById(Serializable id) {
        if (id == null) {
            log.info("菜单信息删除异常，主键ID为null");
            return false;
        }
        return menuMapper.deleteById(id) > 0;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return removeByIds(ids, 200);
    }

    public boolean removeByIds(Collection<? extends Serializable> ids, int batchSize) {
        if (CollUtil.isEmpty(ids)) {
            log.info("菜单信息批量删除异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<? extends List<? extends Serializable>> split = CollUtil.split(ids, batchSize);
        Integer total = split.stream().map(menuMapper::deleteBatchIds).reduce(1, Integer::sum);
        return total == ids.size();
    }

    @Override
    public boolean updateById(Menu menu) {
        if (menu == null) {
            log.info("菜单信息更新异常，角色为null");
            return false;
        }
        return menuMapper.updateById(menu) > 0;
    }

    @Override
    public Menu getById(Serializable id) {
        return menuMapper.selectById(id);
    }

    @Override
    public List<Menu> list(MenuQuery query) {
        return menuMapper.selectByConditions(query);
    }

}
