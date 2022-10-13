package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.upms.domain.repository.FuncRepository;
import com.ticho.upms.infrastructure.entity.Func;
import com.ticho.upms.infrastructure.mapper.FuncMapper;
import com.ticho.upms.interfaces.query.FuncQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 功能号信息 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class FuncRepositoryImpl extends ServiceImpl<FuncMapper, Func> implements FuncRepository {

    @Autowired
    private FuncMapper funcMapper;

    @Override
    public boolean save(Func func) {
        if (func == null) {
            log.info("功能号信息保存异常，对象为null");
            return false;
        }
        return funcMapper.insert(func) == 1;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<Func> funcs) {
        return this.saveBatch(funcs, 200);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<Func> funcs, int batchSize) {
        if (CollUtil.isEmpty(funcs)) {
            log.info("功能号信息批量保存异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<List<Func>> split = CollUtil.split(funcs, batchSize);
        Integer total = split.stream().map(funcMapper::insertBatch).reduce(1, Integer::sum);
        return total == funcs.size();
    }

    @Override
    public boolean removeById(Serializable id) {
        if (id == null) {
            log.info("功能号信息删除异常，主键ID为null");
            return false;
        }
        return funcMapper.deleteById(id) > 0;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return removeByIds(ids, 200);
    }

    public boolean removeByIds(Collection<? extends Serializable> ids, int batchSize) {
        if (CollUtil.isEmpty(ids)) {
            log.info("功能号信息批量删除异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<? extends List<? extends Serializable>> split = CollUtil.split(ids, batchSize);
        Integer total = split.stream().map(funcMapper::deleteBatchIds).reduce(1, Integer::sum);
        return total == ids.size();
    }

    @Override
    public boolean updateById(Func func) {
        if (func == null) {
            log.info("功能号信息更新异常，角色为null");
            return false;
        }
        return funcMapper.updateById(func) > 0;
    }

    @Override
    public Func getById(Serializable id) {
        return funcMapper.selectById(id);
    }

    @Override
    public List<Func> list(FuncQuery query) {
        return funcMapper.selectByConditions(query);
    }

}
