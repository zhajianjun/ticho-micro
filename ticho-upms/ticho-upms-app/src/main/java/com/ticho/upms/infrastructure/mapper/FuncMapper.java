package com.ticho.upms.infrastructure.mapper;

import com.ticho.boot.datasource.mapper.RootMapper;
import com.ticho.upms.infrastructure.entity.Func;
import com.ticho.upms.interfaces.query.FuncQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 功能号信息 mapper
 *
 * @author zhajianjun
 * @date 2022-10-18 09:08
 */
@Repository
public interface FuncMapper extends RootMapper<Func> {

}