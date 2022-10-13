package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.upms.domain.repository.DictRepository;
import com.ticho.upms.infrastructure.entity.Dict;
import com.ticho.upms.infrastructure.mapper.DictMapper;
import com.ticho.upms.interfaces.query.DictQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 数据字典 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class DictRepositoryImpl extends ServiceImpl<DictMapper, Dict> implements DictRepository {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public boolean save(Dict dict) {
        if (dict == null) {
            log.info("数据字典保存异常，对象为null");
            return false;
        }
        return dictMapper.insert(dict) == 1;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<Dict> dicts) {
        return this.saveBatch(dicts, 200);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<Dict> dicts, int batchSize) {
        if (CollUtil.isEmpty(dicts)) {
            log.info("数据字典批量保存异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<List<Dict>> split = CollUtil.split(dicts, batchSize);
        Integer total = split.stream().map(dictMapper::insertBatch).reduce(1, Integer::sum);
        return total == dicts.size();
    }

    @Override
    public boolean removeById(Serializable id) {
        if (id == null) {
            log.info("数据字典删除异常，主键ID为null");
            return false;
        }
        return dictMapper.deleteById(id) > 0;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return removeByIds(ids, 200);
    }

    public boolean removeByIds(Collection<? extends Serializable> ids, int batchSize) {
        if (CollUtil.isEmpty(ids)) {
            log.info("数据字典批量删除异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<? extends List<? extends Serializable>> split = CollUtil.split(ids, batchSize);
        Integer total = split.stream().map(dictMapper::deleteBatchIds).reduce(1, Integer::sum);
        return total == ids.size();
    }

    @Override
    public boolean updateById(Dict dict) {
        if (dict == null) {
            log.info("数据字典更新异常，角色为null");
            return false;
        }
        return dictMapper.updateById(dict) > 0;
    }

    @Override
    public Dict getById(Serializable id) {
        return dictMapper.selectById(id);
    }

    @Override
    public List<Dict> list(DictQuery query) {
        return dictMapper.selectByConditions(query);
    }

}
