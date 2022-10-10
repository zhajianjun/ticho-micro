package com.ticho.upms.domain.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.util.Assert;
import com.ticho.upms.application.service.RoleService;
import com.ticho.upms.domain.repository.RoleRepository;
import com.ticho.upms.infrastructure.entity.Role;
import com.ticho.upms.interfaces.assembler.RoleAssembler;
import com.ticho.upms.interfaces.dto.RoleDTO;
import com.ticho.upms.interfaces.query.RoleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息 服务实现
 *
 * @author zhajianjun
 * @date 2022-10-10 17:28
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(RoleDTO roleDTO) {
        Role role = RoleAssembler.INSTANCE.dtoToEntity(roleDTO);
        Assert.isTrue(roleRepository.save(role), BizErrCode.FAIL, "保存失败");
    }

    @Override
    public void removeById(Serializable id) {
        Assert.isTrue(roleRepository.removeById(id), BizErrCode.FAIL, "删除失败");
    }

    @Override
    public void updateById(RoleDTO roleDTO) {
        Role role = RoleAssembler.INSTANCE.dtoToEntity(roleDTO);
        Assert.isTrue(roleRepository.updateById(role), BizErrCode.FAIL, "修改失败");
    }

    @Override
    public RoleDTO getById(Serializable id) {
        Role role = roleRepository.getById(id);
        return RoleAssembler.INSTANCE.entityToDto(role);
    }

    @Override
    public PageResult<RoleDTO> page(RoleQuery query) {
        // @formatter:off
        query.checkPage();
        Page<Role> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        roleRepository.list(query);
        List<RoleDTO> roleDTOs = page.getResult()
            .stream()
            .map(RoleAssembler.INSTANCE::entityToDto)
            .collect(Collectors.toList());
        return new PageResult<>(page.getPageNum(), page.getPageSize(), page.getTotal(), roleDTOs);
        // @formatter:on
    }
}
