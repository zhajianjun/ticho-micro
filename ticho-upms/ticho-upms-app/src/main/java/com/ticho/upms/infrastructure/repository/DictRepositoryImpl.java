package com.ticho.upms.infrastructure.repository;

import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.DictRepository;
import com.ticho.upms.infrastructure.entity.Dict;
import com.ticho.upms.infrastructure.mapper.DictMapper;
import com.ticho.upms.interfaces.query.DictQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<Dict> list(DictQuery query) {
        return dictMapper.selectByConditions(query);
    }

}
