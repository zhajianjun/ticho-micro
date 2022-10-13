package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.upms.domain.repository.DictTypeRepository;
import com.ticho.upms.infrastructure.entity.DictType;
import com.ticho.upms.infrastructure.mapper.DictTypeMapper;
import com.ticho.upms.interfaces.query.DictTypeQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 数据字典类型 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class DictTypeRepositoryImpl extends ServiceImpl<DictTypeMapper, DictType> implements DictTypeRepository {

    @Autowired
    private DictTypeMapper dictTypeMapper;

    @Override
    public boolean save(DictType dictType) {
        if (dictType == null) {
            log.info("数据字典类型保存异常，对象为null");
            return false;
        }
        return dictTypeMapper.insert(dictType) == 1;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<DictType> dictTypes) {
        return this.saveBatch(dictTypes, 200);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<DictType> dictTypes, int batchSize) {
        if (CollUtil.isEmpty(dictTypes)) {
            log.info("数据字典类型批量保存异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<List<DictType>> split = CollUtil.split(dictTypes, batchSize);
        Integer total = split.stream().map(dictTypeMapper::insertBatch).reduce(1, Integer::sum);
        return total == dictTypes.size();
    }

    @Override
    public boolean removeById(Serializable id) {
        if (id == null) {
            log.info("数据字典类型删除异常，主键ID为null");
            return false;
        }
        return dictTypeMapper.deleteById(id) > 0;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return removeByIds(ids, 200);
    }

    public boolean removeByIds(Collection<? extends Serializable> ids, int batchSize) {
        if (CollUtil.isEmpty(ids)) {
            log.info("数据字典类型批量删除异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<? extends List<? extends Serializable>> split = CollUtil.split(ids, batchSize);
        Integer total = split.stream().map(dictTypeMapper::deleteBatchIds).reduce(1, Integer::sum);
        return total == ids.size();
    }

    @Override
    public boolean updateById(DictType dictType) {
        if (dictType == null) {
            log.info("数据字典类型更新异常，角色为null");
            return false;
        }
        return dictTypeMapper.updateById(dictType) > 0;
    }

    @Override
    public DictType getById(Serializable id) {
        return dictTypeMapper.selectById(id);
    }

    @Override
    public List<DictType> list(DictTypeQuery query) {
        return dictTypeMapper.selectByConditions(query);
    }

}
