package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.util.StrUtil;
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
import java.util.Objects;

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
        wrapper.eq(Objects.nonNull(query.getId()), OpLog::getId, query.getId());
        wrapper.eq(StrUtil.isNotBlank(query.getUrl()), OpLog::getUrl, query.getUrl());
        wrapper.eq(StrUtil.isNotBlank(query.getType()), OpLog::getType, query.getType());
        wrapper.eq(StrUtil.isNotBlank(query.getMethod()), OpLog::getMethod, query.getMethod());
        wrapper.eq(StrUtil.isNotBlank(query.getParams()), OpLog::getParams, query.getParams());
        wrapper.eq(StrUtil.isNotBlank(query.getMessage()), OpLog::getMessage, query.getMessage());
        wrapper.eq(Objects.nonNull(query.getTotalTime()), OpLog::getTotalTime, query.getTotalTime());
        wrapper.eq(StrUtil.isNotBlank(query.getIp()), OpLog::getIp, query.getIp());
        wrapper.eq(StrUtil.isNotBlank(query.getOperateBy()), OpLog::getOperateBy, query.getOperateBy());
        wrapper.eq(Objects.nonNull(query.getOperateTime()), OpLog::getOperateTime, query.getOperateTime());
        wrapper.eq(Objects.nonNull(query.getIsErr()), OpLog::getIsErr, query.getIsErr());
        wrapper.eq(StrUtil.isNotBlank(query.getErrMessage()), OpLog::getErrMessage, query.getErrMessage());
        return list(wrapper);
    }

}
