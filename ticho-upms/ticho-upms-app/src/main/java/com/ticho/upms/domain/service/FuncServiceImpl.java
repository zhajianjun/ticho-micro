package com.ticho.upms.domain.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.util.Assert;
import com.ticho.upms.application.service.FuncService;
import com.ticho.upms.domain.repository.FuncRepository;
import com.ticho.upms.infrastructure.entity.Func;
import com.ticho.upms.interfaces.assembler.FuncAssembler;
import com.ticho.upms.interfaces.dto.FuncDTO;
import com.ticho.upms.interfaces.query.FuncQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能号信息 服务实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Service
public class FuncServiceImpl implements FuncService {

    @Autowired
    private FuncRepository funcRepository;

    @Override
    public void save(FuncDTO funcDTO) {
        Func func = FuncAssembler.INSTANCE.dtoToEntity(funcDTO);
        Assert.isTrue(funcRepository.save(func), BizErrCode.FAIL, "保存失败");
    }

    @Override
    public void removeById(Serializable id) {
        Assert.isTrue(funcRepository.removeById(id), BizErrCode.FAIL, "删除失败");
    }

    @Override
    public void updateById(FuncDTO funcDTO) {
        Func func = FuncAssembler.INSTANCE.dtoToEntity(funcDTO);
        Assert.isTrue(funcRepository.updateById(func), BizErrCode.FAIL, "修改失败");
    }

    @Override
    public FuncDTO getById(Serializable id) {
        Func func = funcRepository.getById(id);
        return FuncAssembler.INSTANCE.entityToDto(func);
    }

    @Override
    public PageResult<FuncDTO> page(FuncQuery query) {
        // @formatter:off
        query.checkPage();
        Page<Func> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        funcRepository.list(query);
        List<FuncDTO> funcDTOs = page.getResult()
            .stream()
            .map(FuncAssembler.INSTANCE::entityToDto)
            .collect(Collectors.toList());
        return new PageResult<>(page.getPageNum(), page.getPageSize(), page.getTotal(), funcDTOs);
        // @formatter:on
    }
}
