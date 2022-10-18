package com.ticho.upms.infrastructure.mapper;

import com.ticho.boot.datasource.mapper.RootMapper;
import com.ticho.upms.infrastructure.entity.OpLog;
import org.springframework.stereotype.Repository;

/**
 * 日志信息 mapper
 *
 * @author zhajianjun
 * @date 2022-10-18 09:08
 */
@Repository
public interface OpLogMapper extends RootMapper<OpLog> {

}