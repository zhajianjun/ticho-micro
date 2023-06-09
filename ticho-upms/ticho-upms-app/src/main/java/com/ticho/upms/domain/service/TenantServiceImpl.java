package com.ticho.upms.domain.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.util.Assert;
import com.ticho.boot.web.util.valid.ValidUtil;
import com.ticho.upms.application.service.TenantService;
import com.ticho.upms.domain.repository.TenantRepository;
import com.ticho.upms.infrastructure.core.enums.TenantStatus;
import com.ticho.upms.infrastructure.entity.Tenant;
import com.ticho.upms.interfaces.assembler.TenantAssembler;
import com.ticho.upms.interfaces.dto.TenantDTO;
import com.ticho.upms.interfaces.dto.TenantSignUpDTO;
import com.ticho.upms.interfaces.query.TenantQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 租户信息 服务实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Service
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public void signUp(TenantSignUpDTO tenantSignUpDTO) {
        ValidUtil.valid(tenantSignUpDTO);
        Tenant tenant = TenantAssembler.INSTANCE.signUpDtoToEntity(tenantSignUpDTO);
        boolean exists = tenantRepository.exists(tenant.getTenantId(), null);
        Assert.isTrue(!exists, BizErrCode.FAIL, "租户已存在");
        tenant.setStatus(TenantStatus.NOT_ACTIVE.code());
        tenantRepository.save(tenant);
    }

    @Override
    public void confirm(String tenantId) {
        Assert.isNotBlank(tenantId, BizErrCode.PARAM_ERROR, "租户id不能为空");
        Tenant tenant = new Tenant();
        tenant.setTenantId(tenantId);
        tenant.setStatus(TenantStatus.NORMAL.code());
        boolean updated = tenantRepository.updateByTenantId(tenant);
        Assert.isTrue(updated, BizErrCode.FAIL, "确认失败");
    }

    @Override
    public void save(TenantDTO tenantDTO) {
        Tenant tenant = TenantAssembler.INSTANCE.dtoToEntity(tenantDTO);
        Assert.isTrue(tenantRepository.save(tenant), BizErrCode.FAIL, "保存失败");
    }

    @Override
    public void removeById(Serializable id) {
        Assert.isTrue(tenantRepository.removeById(id), BizErrCode.FAIL, "删除失败");
    }

    @Override
    public void updateById(TenantDTO tenantDTO) {
        Tenant tenant = TenantAssembler.INSTANCE.dtoToEntity(tenantDTO);
        Assert.isTrue(tenantRepository.updateById(tenant), BizErrCode.FAIL, "修改失败");
    }

    @Override
    public TenantDTO getById(Serializable id) {
        Tenant tenant = tenantRepository.getById(id);
        return TenantAssembler.INSTANCE.entityToDto(tenant);
    }

    @Override
    public PageResult<TenantDTO> page(TenantQuery query) {
        // @formatter:off
        query.checkPage();
        Page<Tenant> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        tenantRepository.list(query);
        List<TenantDTO> tenantDTOs = page.getResult()
            .stream()
            .map(TenantAssembler.INSTANCE::entityToDto)
            .collect(Collectors.toList());
        return new PageResult<>(page.getPageNum(), page.getPageSize(), page.getTotal(), tenantDTOs);
        // @formatter:on
    }
}
