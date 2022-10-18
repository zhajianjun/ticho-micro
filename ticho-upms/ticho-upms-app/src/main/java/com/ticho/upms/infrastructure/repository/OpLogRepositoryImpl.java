package com.ticho.upms.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.OpLogRepository;
import com.ticho.upms.infrastructure.entity.OpLog;
import com.ticho.upms.infrastructure.mapper.OpLogMapper;
import com.ticho.upms.interfaces.query.OpLogQuery;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public List<OpLog> list(OpLogQuery query) {
        LambdaQueryWrapper<OpLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OpLog::getId, query.getId());
        wrapper.eq(OpLog::getUrl, query.getUrl());
        wrapper.eq(OpLog::getType, query.getType());
        wrapper.eq(OpLog::getMethod, query.getMethod());
        wrapper.eq(OpLog::getParams, query.getParams());
        wrapper.eq(OpLog::getMessage, query.getMessage());
        wrapper.eq(OpLog::getTotalTime, query.getTotalTime());
        wrapper.eq(OpLog::getIp, query.getIp());
        wrapper.eq(OpLog::getOperateBy, query.getOperateBy());
        wrapper.eq(OpLog::getOperateTime, query.getOperateTime());
        wrapper.eq(OpLog::getIsErr, query.getIsErr());
        wrapper.eq(OpLog::getErrMessage, query.getErrMessage());
        return list(wrapper);
    }

}
