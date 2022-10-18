package com.ticho.upms.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.DictTypeRepository;
import com.ticho.upms.infrastructure.entity.DictType;
import com.ticho.upms.infrastructure.mapper.DictTypeMapper;
import com.ticho.upms.interfaces.query.DictTypeQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典类型 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class DictTypeRepositoryImpl extends RootServiceImpl<DictTypeMapper, DictType> implements DictTypeRepository {

    @Autowired
    private DictTypeMapper dictTypeMapper;

    @Override
    public List<DictType> list(DictTypeQuery query) {
        LambdaQueryWrapper<DictType> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DictType::getId, query.getId());
        wrapper.eq(DictType::getCode, query.getCode());
        wrapper.eq(DictType::getName, query.getName());
        wrapper.eq(DictType::getIsSys, query.getIsSys());
        wrapper.eq(DictType::getRemark, query.getRemark());
        wrapper.eq(DictType::getVersion, query.getVersion());
        wrapper.eq(DictType::getCreateBy, query.getCreateBy());
        wrapper.eq(DictType::getCreateTime, query.getCreateTime());
        wrapper.eq(DictType::getUpdateBy, query.getUpdateBy());
        wrapper.eq(DictType::getUpdateTime, query.getUpdateTime());
        wrapper.eq(DictType::getIsDelete, query.getIsDelete());
        return list(wrapper);
    }

}
