package com.ticho.upms.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.DictRepository;
import com.ticho.upms.infrastructure.entity.Dict;
import com.ticho.upms.infrastructure.mapper.DictMapper;
import com.ticho.upms.interfaces.query.DictQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class DictRepositoryImpl extends RootServiceImpl<DictMapper, Dict> implements DictRepository {

    @Override
    public List<Dict> list(DictQuery query) {
        LambdaQueryWrapper<Dict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Dict::getId, query.getId());
        wrapper.eq(Dict::getPid, query.getPid());
        wrapper.eq(Dict::getTypeId, query.getTypeId());
        wrapper.eq(Dict::getCode, query.getCode());
        wrapper.eq(Dict::getName, query.getName());
        wrapper.eq(Dict::getSort, query.getSort());
        wrapper.eq(Dict::getLevel, query.getLevel());
        wrapper.eq(Dict::getStructure, query.getStructure());
        wrapper.eq(Dict::getStatus, query.getStatus());
        wrapper.eq(Dict::getRemark, query.getRemark());
        wrapper.eq(Dict::getVersion, query.getVersion());
        wrapper.eq(Dict::getCreateBy, query.getCreateBy());
        wrapper.eq(Dict::getCreateTime, query.getCreateTime());
        wrapper.eq(Dict::getUpdateBy, query.getUpdateBy());
        wrapper.eq(Dict::getUpdateTime, query.getUpdateTime());
        wrapper.eq(Dict::getIsDelete, query.getIsDelete());
        return list(wrapper);
    }

}
