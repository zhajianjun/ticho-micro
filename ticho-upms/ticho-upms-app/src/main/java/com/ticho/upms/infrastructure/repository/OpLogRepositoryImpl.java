package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.upms.domain.repository.OpLogRepository;
import com.ticho.upms.infrastructure.entity.OpLog;
import com.ticho.upms.infrastructure.mapper.OpLogMapper;
import com.ticho.upms.interfaces.query.OpLogQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 日志信息 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class OpLogRepositoryImpl extends ServiceImpl<OpLogMapper, OpLog> implements OpLogRepository {

    @Autowired
    private OpLogMapper opLogMapper;

    @Override
    public boolean save(OpLog opLog) {
        if (opLog == null) {
            log.info("日志信息保存异常，对象为null");
            return false;
        }
        return opLogMapper.insert(opLog) == 1;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<OpLog> opLogs) {
        return this.saveBatch(opLogs, 200);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<OpLog> opLogs, int batchSize) {
        if (CollUtil.isEmpty(opLogs)) {
            log.info("日志信息批量保存异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<List<OpLog>> split = CollUtil.split(opLogs, batchSize);
        Integer total = split.stream().map(opLogMapper::insertBatch).reduce(1, Integer::sum);
        return total == opLogs.size();
    }

    @Override
    public boolean removeById(Serializable id) {
        if (id == null) {
            log.info("日志信息删除异常，主键ID为null");
            return false;
        }
        return opLogMapper.deleteById(id) > 0;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return removeByIds(ids, 200);
    }

    public boolean removeByIds(Collection<? extends Serializable> ids, int batchSize) {
        if (CollUtil.isEmpty(ids)) {
            log.info("日志信息批量删除异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<? extends List<? extends Serializable>> split = CollUtil.split(ids, batchSize);
        Integer total = split.stream().map(opLogMapper::deleteBatchIds).reduce(1, Integer::sum);
        return total == ids.size();
    }

    @Override
    public boolean updateById(OpLog opLog) {
        if (opLog == null) {
            log.info("日志信息更新异常，角色为null");
            return false;
        }
        return opLogMapper.updateById(opLog) > 0;
    }

    @Override
    public OpLog getById(Serializable id) {
        return opLogMapper.selectById(id);
    }

    @Override
    public List<OpLog> list(OpLogQuery query) {
        return opLogMapper.selectByConditions(query);
    }

}
