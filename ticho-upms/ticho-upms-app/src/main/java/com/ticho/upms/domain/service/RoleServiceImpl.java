package com.ticho.upms.domain.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.util.Assert;
import com.ticho.boot.web.util.valid.ValidUtil;
import com.ticho.upms.application.service.RoleService;
import com.ticho.upms.domain.handle.UpmsHandle;
import com.ticho.upms.domain.repository.RoleFuncRepository;
import com.ticho.upms.domain.repository.RoleMenuRepository;
import com.ticho.upms.domain.repository.RoleRepository;
import com.ticho.upms.infrastructure.entity.Role;
import com.ticho.upms.infrastructure.entity.RoleMenu;
import com.ticho.upms.interfaces.assembler.RoleAssembler;
import com.ticho.upms.interfaces.dto.RoleDTO;
import com.ticho.upms.interfaces.dto.RoleFuncDTO;
import com.ticho.upms.interfaces.dto.RoleMenuDTO;
import com.ticho.upms.interfaces.query.RoleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息 服务实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Service
public class RoleServiceImpl extends UpmsHandle implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMenuRepository roleMenuRepository;

    @Autowired
    private RoleFuncRepository roleFuncRepository;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindMenu(RoleMenuDTO roleMenuDTO) {
        // @formatter:off
        ValidUtil.valid(roleMenuDTO);
        Long roleId = roleMenuDTO.getRoleId();
        List<Long> menuIds = roleMenuDTO.getMenuIds();
        List<RoleMenu> roleMenus = roleMenuRepository.listByRoleIds(Collections.singletonList(roleId));
        List<Long> selectMenuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        // 需要添加角色用户关联关系
        List<RoleMenu> addRoleMenus = menuIds
            .stream()
            .filter(x-> !selectMenuIds.contains(x))
            .map(x-> convertToRoleMenu(roleId, x))
            .collect(Collectors.toList());
        // 需要删除的菜单id列表
        List<Long> removeMenuIds = menuIds
            .stream()
            .filter(x-> !menuIds.contains(x))
            .collect(Collectors.toList());
        // 删除角色绑定的菜单
        roleMenuRepository.removeByRoleIdAndMenuIds(roleId, removeMenuIds);
        // 删除角色绑定菜单下的功能号
        roleFuncRepository.removeByRoleIdAndMenuIds(roleId, removeMenuIds);
        // 保存角色绑定的菜单
        roleMenuRepository.saveBatch(addRoleMenus);
        // @formatter:on
    }

    @Override
    public void bindFunc(RoleFuncDTO roleFuncDTO) {
        ValidUtil.valid(roleFuncDTO);
        // TODO
    }

    private RoleMenu convertToRoleMenu(Long roleId, Long x) {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(roleId);
        roleMenu.setMenuId(x);
        return roleMenu;
    }

}
