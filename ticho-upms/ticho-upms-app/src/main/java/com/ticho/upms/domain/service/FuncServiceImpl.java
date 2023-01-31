package com.ticho.upms.domain.service;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.util.Assert;
import com.ticho.boot.web.util.SpringContext;
import com.ticho.upms.application.service.FuncService;
import com.ticho.upms.domain.repository.FuncRepository;
import com.ticho.upms.domain.repository.MenuFuncRepository;
import com.ticho.upms.domain.repository.RoleFuncRepository;
import com.ticho.upms.infrastructure.entity.Func;
import com.ticho.upms.interfaces.assembler.FuncAssembler;
import com.ticho.upms.interfaces.dto.FuncDTO;
import com.ticho.upms.interfaces.query.FuncQuery;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @Autowired
    private MenuFuncRepository menuFuncRepository;

    @Autowired
    private RoleFuncRepository roleFuncRepository;

    @Override
    public void save(FuncDTO funcDTO) {
        Func func = FuncAssembler.INSTANCE.dtoToEntity(funcDTO);
        Assert.isTrue(funcRepository.save(func), BizErrCode.FAIL, "保存失败");
    }

    @Override
    public void saveOrUpdateByCode(FuncDTO funcDTO) {
        Func func = FuncAssembler.INSTANCE.dtoToEntity(funcDTO);
        Assert.isTrue(funcRepository.saveOrUpdateByCode(func), BizErrCode.FAIL, "保存或者更新失败");
    }

    @Override
    public void saveOrUpdateBatchByCode(Collection<FuncDTO> funcDTOs) {
        List<Func> funcs = FuncAssembler.INSTANCE.dtoToEntitys(funcDTOs);
        Assert.isTrue(funcRepository.saveOrUpdateBatchByCode(funcs), BizErrCode.FAIL, "保存或者更新失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        Assert.isTrue(funcRepository.removeById(id), BizErrCode.FAIL, "删除失败");
        List<Long> funcIds = Collections.singletonList(id);
        menuFuncRepository.removeByFuncIds(funcIds);
        roleFuncRepository.removeByFuncIds(funcIds);
    }

    @Override
    public void updateById(FuncDTO funcDTO) {
        Func func = FuncAssembler.INSTANCE.dtoToEntity(funcDTO);
        Assert.isTrue(funcRepository.updateById(func), BizErrCode.FAIL, "修改失败");
    }

    @Override
    public FuncDTO getById(Long id) {
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

    @Override
    public void init() {
        // @formatter:off
        RequestMappingHandlerMapping mapping = SpringContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<Func> funcs = map.values()
            .stream()
            .map(this::getFunc)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        boolean updated = funcRepository.saveOrUpdateBatchByCode(funcs);
        Assert.isTrue(updated, BizErrCode.FAIL, "更新功能号失败");
    }

    private Func getFunc(HandlerMethod handlerMethod) {
        PreAuthorize preAuthorize = handlerMethod.getMethodAnnotation(PreAuthorize.class);
        if (preAuthorize == null) {
            return null;
        }
        String value = preAuthorize.value();
        if (StrUtil.isBlank(value)) {
            return null;
        }
        int start = value.indexOf("'") + 1;
        int end = value.lastIndexOf("'");
        value = value.substring(start, end);
        ApiOperation apiOperation = handlerMethod.getMethodAnnotation(ApiOperation.class);
        String name = handlerMethod.getBeanType().toString();
        Func func = new Func();
        func.setCode(value);
        func.setName(apiOperation == null ? name : apiOperation.value());
        return func;
    }
}
