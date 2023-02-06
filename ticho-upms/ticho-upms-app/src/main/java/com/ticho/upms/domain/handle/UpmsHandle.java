package com.ticho.upms.domain.handle;

import cn.hutool.core.collection.CollUtil;
import com.ticho.upms.domain.repository.MenuRepository;
import com.ticho.upms.domain.repository.RoleMenuRepository;
import com.ticho.upms.domain.repository.RoleRepository;
import com.ticho.upms.domain.repository.UserRepository;
import com.ticho.upms.domain.repository.UserRoleRepository;
import com.ticho.upms.infrastructure.core.util.TreeUtil;
import com.ticho.upms.infrastructure.entity.Menu;
import com.ticho.upms.infrastructure.entity.Role;
import com.ticho.upms.infrastructure.entity.RoleMenu;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.infrastructure.entity.UserRole;
import com.ticho.upms.interfaces.assembler.MenuAssembler;
import com.ticho.upms.interfaces.assembler.RoleAssembler;
import com.ticho.upms.interfaces.assembler.UserAssembler;
import com.ticho.upms.interfaces.dto.MenuDtlDTO;
import com.ticho.upms.interfaces.dto.RoleDTO;
import com.ticho.upms.interfaces.dto.RoleMenuDtlDTO;
import com.ticho.upms.interfaces.dto.UserRoleMenuDtlDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 *
 * @author zhajianjun
 * @date 2023-01-31 10:32
 */
public class UpmsHandle {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMenuRepository roleMenuRepository;

    @Autowired
    private MenuRepository menuRepository;

    /**
     * 根据用户名查询用户
     *
     * @param tenantId 租户id
     * @param username 用户名
     * @return {@link UserRoleMenuDtlDTO}
     */
    public UserRoleMenuDtlDTO getUserDtl(String tenantId, String username) {
        User user = userRepository.getByUsername(tenantId, username);
        UserRoleMenuDtlDTO userRoleMenuDtlDTO = UserAssembler.INSTANCE.entityToDtl(user);
        if (userRoleMenuDtlDTO == null) {
            return null;
        }
        List<UserRole> userRoles = userRoleRepository.listByUserId(user.getId());
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        RoleMenuDtlDTO roleMenuFuncDtl = mergeMenuByRoleIds(roleIds, false);
        if (roleMenuFuncDtl == null) {
            return null;
        }
        userRoleMenuDtlDTO.setRoleIds(roleMenuFuncDtl.getRoleIds());
        userRoleMenuDtlDTO.setRoleCodes(roleMenuFuncDtl.getRoleCodes());
        userRoleMenuDtlDTO.setMenuIds(roleMenuFuncDtl.getMenuIds());
        userRoleMenuDtlDTO.setRoles(roleMenuFuncDtl.getRoles());
        userRoleMenuDtlDTO.setMenus(roleMenuFuncDtl.getMenus());
        return userRoleMenuDtlDTO;
    }


    /**
     * 合并菜单按角色id
     *
     * @param roleCodes 角色code列表
     * @param showAll 显示所有信息，匹配到的信息，设置匹配字段checkbox=true
     * @return {@link RoleMenuDtlDTO}
     */
    public RoleMenuDtlDTO mergeMenuByRoleCodes(List<String> roleCodes, boolean showAll) {
        if (CollUtil.isEmpty(roleCodes)) {
            return null;
        }
        // 根据角色id列表 查询角色信息
        List<Role> roles = roleRepository.listByCodes(roleCodes);
        return getRoleMenuDtl(roles, showAll);
    }

    /**
     * 合并菜单按角色id
     *
     * @param roleIds 角色id列表
     * @param showAll 显示所有信息，匹配到的信息，设置匹配字段checkbox=true
     * @return {@link RoleMenuDtlDTO}
     */
    public RoleMenuDtlDTO mergeMenuByRoleIds(List<Long> roleIds, boolean showAll) {
        if (CollUtil.isEmpty(roleIds)) {
            return null;
        }
        // 1.根据角色id列表查询角色信息、菜单信息、角色菜单信息、角色权限标识信息、菜单权限标识信息
        // 根据角色id列表 查询角色信息
        List<Role> roles = roleRepository.listByIds(roleIds);
        return getRoleMenuDtl(roles, showAll);
    }

    private RoleMenuDtlDTO getRoleMenuDtl(List<Role> roles, boolean showAll) {
        // @formatter:off
        List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        if (CollUtil.isEmpty(roleIds)) {
            return null;
        }
        // 根据角色id列表 查询角色菜单关联信息
        List<RoleMenu> roleMenus = roleMenuRepository.listByRoleIds(roleIds);
        // 合并的角色后所有的菜单
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        // 菜单信息
        List<Menu> menus = showAll ? menuRepository.list() : menuRepository.listByIds(menuIds);
        // 查询到的角色信息组装填充
        List<RoleDTO> roleDtos = new ArrayList<>();
        roleIds = new ArrayList<>();
        List<String> roleCodes = new ArrayList<>();
        for (Role role : roles) {
            RoleDTO roleDTO = RoleAssembler.INSTANCE.entityToDto(role);
            roleIds.add(role.getId());
            roleCodes.add(role.getCode());
            roleDtos.add(roleDTO);
        }
        List<String> perms = new ArrayList<>();
        // 菜单信息过滤规则
        Predicate<MenuDtlDTO> menuFilter = null;
        // 菜单信息操作
        Consumer<MenuDtlDTO> menuPeek = x-> perms.addAll(x.getPerms());
        // 如果展示全部字段，匹配的数据进行填充checkbox=true
        if (showAll) {
            menuFilter = x -> true;
            menuPeek = x -> {
                perms.addAll(x.getPerms());
                x.setCheckbox(menuIds.contains(x.getId()));
            };
        }
        // 根据菜单信息，填充权限标识信息
        List<MenuDtlDTO> menuFuncDtls = getMenuDtls(menus, menuFilter, menuPeek);
        // 菜单信息规整为树结构
        List<MenuDtlDTO> tree = TreeUtil.tree(menuFuncDtls, 0L, x -> {});
        RoleMenuDtlDTO roleMenuDtlDTO = new RoleMenuDtlDTO();
        roleMenuDtlDTO.setRoleIds(roleIds);
        roleMenuDtlDTO.setMenus(tree);
        roleMenuDtlDTO.setMenuIds(menuIds);
        roleMenuDtlDTO.setRoleCodes(roleCodes);
        roleMenuDtlDTO.setRoles(roleDtos);
        roleMenuDtlDTO.setPerms(perms);
        return roleMenuDtlDTO;
        // @formatter:on
    }

    // @formatter:off

    /**
     * 菜单信息转换、过滤、执行规则信息
     *
     * @param menus 菜单
     * @param filter 过滤规则
     * @param peek 执行规则
     * @return {@link List}<{@link MenuDtlDTO}>
     */
    public List<MenuDtlDTO> getMenuDtls(List<Menu> menus, Predicate<MenuDtlDTO> filter, Consumer<MenuDtlDTO> peek) {
        if (filter == null) {
            filter = x -> true;
        }
        if (peek == null) {
            peek = x -> {};
        }
        return menus
            .stream()
            .map(MenuAssembler.INSTANCE::entityToDtlDto)
            .filter(filter)
            .peek(peek)
            .sorted(Comparator.comparing(MenuDtlDTO::getParentId).thenComparing(Comparator.nullsLast(Comparator.comparing(MenuDtlDTO::getSort))))
            .collect(Collectors.toList());
    }

    // @formatter:on

}
