package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.boot.web.util.CloudIdUtil;
import com.ticho.upms.domain.repository.FuncRepository;
import com.ticho.upms.infrastructure.entity.DictType;
import com.ticho.upms.infrastructure.entity.Func;
import com.ticho.upms.infrastructure.mapper.FuncMapper;
import com.ticho.upms.interfaces.query.DictTypeQuery;
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
import java.util.Objects;
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
public class FuncRepositoryImpl extends RootServiceImpl<FuncMapper, Func> implements FuncRepository {

    @Autowired
    private FuncMapper funcMapper;

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
        LambdaQueryWrapper<Func> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Objects.nonNull(query.getId()), Func::getId, query.getId());
        wrapper.eq(StrUtil.isNotBlank(query.getCode()), Func::getCode, query.getCode());
        wrapper.eq(StrUtil.isNotBlank(query.getName()), Func::getName, query.getName());
        wrapper.eq(Objects.nonNull(query.getRemark()), Func::getRemark, query.getRemark());
        wrapper.eq(Objects.nonNull(query.getVersion()), Func::getVersion, query.getVersion());
        wrapper.eq(StrUtil.isNotBlank(query.getCreateBy()), Func::getCreateBy, query.getCreateBy());
        wrapper.eq(Objects.nonNull(query.getCreateTime()), Func::getCreateTime, query.getCreateTime());
        wrapper.eq(StrUtil.isNotBlank(query.getUpdateBy()), Func::getUpdateBy, query.getUpdateBy());
        wrapper.eq(Objects.nonNull(query.getUpdateTime()), Func::getUpdateTime, query.getUpdateTime());
        wrapper.eq(Objects.nonNull(query.getIsDelete()), Func::getIsDelete, query.getIsDelete());
        return list(wrapper);
    }

}
