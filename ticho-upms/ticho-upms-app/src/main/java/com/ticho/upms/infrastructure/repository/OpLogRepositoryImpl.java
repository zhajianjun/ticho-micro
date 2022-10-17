package com.ticho.upms.infrastructure.repository;

import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.OpLogRepository;
import com.ticho.upms.infrastructure.entity.OpLog;
import com.ticho.upms.infrastructure.mapper.OpLogMapper;
import com.ticho.upms.interfaces.query.OpLogQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 日志信息 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class OpLogRepositoryImpl extends RootServiceImpl<OpLogMapper, OpLog> implements OpLogRepository {

    @Autowired
    private OpLogMapper opLogMapper;

    @Override
    public List<OpLog> list(OpLogQuery query) {
        return opLogMapper.selectByConditions(query);
    }

}
