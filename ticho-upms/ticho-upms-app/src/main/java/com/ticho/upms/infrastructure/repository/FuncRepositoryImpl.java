package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.boot.web.util.CloudIdUtil;
import com.ticho.upms.domain.repository.FuncRepository;
import com.ticho.upms.infrastructure.entity.Func;
import com.ticho.upms.infrastructure.mapper.FuncMapper;
import com.ticho.upms.interfaces.query.FuncQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        Integer total = split
            .stream()
            .map(funcMapper::insertBatch)
            .reduce(1, Integer::sum);
        return total == funcs.size();
    }

    @Override
    public boolean saveOrUpdateByCode(Func func) {
        String code = func.getCode();
        Func selected = getByCode(code);
        if (selected == null) {
            return save(func);
        }
        func.setId(selected.getId());
        return updateById(func);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateBatchByCode(Collection<Func> funcs, int batchSize) {
        if (CollUtil.isEmpty(funcs)) {
            log.info("功能号信息批量保存更新异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<List<Func>> split = CollUtil.split(funcs, batchSize);
        return split.stream().allMatch(this::saveOrUpdateBatchHandle);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateBatchHandle(Collection<Func> funcs) {
        List<String> codes = funcs.stream().map(Func::getCode).collect(Collectors.toList());
        LambdaQueryWrapper<Func> wrapper = Wrappers.lambdaQuery();
        wrapper.in(Func::getCode, codes);
        List<Func> list = list(wrapper);
        Map<String, Func> funcMap = list.stream().collect(Collectors.toMap(Func::getCode, Function.identity()));
        List<Func> saves = new ArrayList<>();
        List<Func> updates = new ArrayList<>();
        for (Func func : funcs) {
            String code = func.getCode();
            if (funcMap.containsKey(code)) {
                Func selected = funcMap.get(code);
                func.setId(selected.getId());
                updates.add(func);
                continue;
            }
            func.setId(CloudIdUtil.getId());
            func.setIsDelete(0);
            saves.add(func);
        }
        boolean result = true;
        if (!saves.isEmpty()) {
            result = saveBatch(saves);
        }
        if (!updates.isEmpty()) {
            result = result && updateBatchById(updates);
        }
        return result;
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
        if (id == null) {
            log.info("功能号信息为空，主键ID为null");
            return null;
        }
        return funcMapper.selectById(id);
    }

    @Override
    public Func getByCode(String code) {
        if (code == null) {
            log.info("功能号信息为空，功能编码为null");
            return null;
        }
        LambdaQueryWrapper<Func> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Func::getCode, code);
        wrapper.last("limit 1");
        return funcMapper.selectOne(wrapper);
    }

    @Override
    public List<Func> list(FuncQuery query) {
        return funcMapper.selectByConditions(query);
    }

}
