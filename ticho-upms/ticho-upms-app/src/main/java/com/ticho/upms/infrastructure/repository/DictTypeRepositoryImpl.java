package com.ticho.upms.infrastructure.repository;

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
        return dictTypeMapper.selectByConditions(query);
    }

}
