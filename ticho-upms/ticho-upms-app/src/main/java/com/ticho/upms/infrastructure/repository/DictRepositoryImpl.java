package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.util.StrUtil;
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
import java.util.Objects;

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
        wrapper.eq(Objects.nonNull(query.getId()), Dict::getId, query.getId());
        wrapper.eq(Objects.nonNull(query.getPid()), Dict::getPid, query.getPid());
        wrapper.eq(Objects.nonNull(query.getTypeId()), Dict::getTypeId, query.getTypeId());
        wrapper.eq(StrUtil.isNotBlank(query.getCode()), Dict::getCode, query.getCode());
        wrapper.eq(StrUtil.isNotBlank(query.getName()), Dict::getName, query.getName());
        wrapper.eq(Objects.nonNull(query.getSort()), Dict::getSort, query.getSort());
        wrapper.eq(Objects.nonNull(query.getLevel()), Dict::getLevel, query.getLevel());
        wrapper.eq(StrUtil.isNotBlank(query.getStructure()), Dict::getStructure, query.getStructure());
        wrapper.eq(Objects.nonNull(query.getStatus()), Dict::getStatus, query.getStatus());
        wrapper.eq(StrUtil.isNotBlank(query.getRemark()), Dict::getRemark, query.getRemark());
        wrapper.eq(Objects.nonNull(query.getVersion()), Dict::getVersion, query.getVersion());
        wrapper.eq(StrUtil.isNotBlank(query.getCreateBy()), Dict::getCreateBy, query.getCreateBy());
        wrapper.eq(Objects.nonNull(query.getCreateTime()), Dict::getCreateTime, query.getCreateTime());
        wrapper.eq(StrUtil.isNotBlank(query.getUpdateBy()), Dict::getUpdateBy, query.getUpdateBy());
        wrapper.eq(Objects.nonNull(query.getUpdateTime()), Dict::getUpdateTime, query.getUpdateTime());
        wrapper.eq(Objects.nonNull(query.getIsDelete()), Dict::getIsDelete, query.getIsDelete());
        return list(wrapper);
    }

}
