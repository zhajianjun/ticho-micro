package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.DictTypeRepository;
import com.ticho.upms.infrastructure.entity.DictType;
import com.ticho.upms.infrastructure.mapper.DictTypeMapper;
import com.ticho.upms.interfaces.query.DictTypeQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 数据字典类型 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class DictTypeRepositoryImpl extends RootServiceImpl<DictTypeMapper, DictType> implements DictTypeRepository {

    @Override
    public List<DictType> list(DictTypeQuery query) {
        LambdaQueryWrapper<DictType> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Objects.nonNull(query.getId()), DictType::getId, query.getId());
        wrapper.eq(StrUtil.isNotBlank(query.getCode()), DictType::getCode, query.getCode());
        wrapper.eq(StrUtil.isNotBlank(query.getName()), DictType::getName, query.getName());
        wrapper.eq(Objects.nonNull(query.getIsSys()), DictType::getIsSys, query.getIsSys());
        wrapper.eq(StrUtil.isNotBlank(query.getRemark()), DictType::getRemark, query.getRemark());
        wrapper.eq(Objects.nonNull(query.getVersion()), DictType::getVersion, query.getVersion());
        wrapper.eq(StrUtil.isNotBlank(query.getCreateBy()), DictType::getCreateBy, query.getCreateBy());
        wrapper.eq(Objects.nonNull(query.getCreateTime()), DictType::getCreateTime, query.getCreateTime());
        wrapper.eq(StrUtil.isNotBlank(query.getUpdateBy()), DictType::getUpdateBy, query.getUpdateBy());
        wrapper.eq(Objects.nonNull(query.getUpdateTime()), DictType::getUpdateTime, query.getUpdateTime());
        wrapper.eq(Objects.nonNull(query.getIsDelete()), DictType::getIsDelete, query.getIsDelete());
        return list(wrapper);
    }

}
