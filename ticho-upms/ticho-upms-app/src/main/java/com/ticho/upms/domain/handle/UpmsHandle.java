package com.ticho.upms.domain.handle;

import cn.hutool.core.collection.CollUtil;
import com.ticho.upms.domain.repository.MenuFuncRepository;
import com.ticho.upms.domain.repository.MenuRepository;
import com.ticho.upms.domain.repository.RoleFuncRepository;
import com.ticho.upms.domain.repository.RoleMenuRepository;
import com.ticho.upms.domain.repository.RoleRepository;
import com.ticho.upms.domain.repository.UserRepository;
import com.ticho.upms.domain.repository.UserRoleRepository;
import com.ticho.upms.infrastructure.core.util.TreeUtil;
import com.ticho.upms.infrastructure.entity.Func;
import com.ticho.upms.infrastructure.entity.Menu;
import com.ticho.upms.infrastructure.entity.Role;
import com.ticho.upms.infrastructure.entity.RoleFunc;
import com.ticho.upms.infrastructure.entity.RoleMenu;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.infrastructure.entity.UserRole;
import com.ticho.upms.interfaces.assembler.FuncAssembler;
import com.ticho.upms.interfaces.assembler.MenuAssembler;
import com.ticho.upms.interfaces.assembler.RoleAssembler;
import com.ticho.upms.interfaces.assembler.UserAssembler;
import com.ticho.upms.interfaces.dto.FuncDTO;
import com.ticho.upms.interfaces.dto.MenuFuncDtlDTO;
import com.ticho.upms.interfaces.dto.RoleDTO;
import com.ticho.upms.interfaces.dto.RoleMenuFuncDtlDTO;
import com.ticho.upms.interfaces.dto.UserRoleMenuFuncDtlDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
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
    private RoleFuncRepository roleFuncRepository;

    @Autowired
    private MenuFuncRepository menuFuncRepository;

    @Autowired
    private MenuRepository menuRepository;

    /**
     * 根据用户名查询用户
     *
     * @param tenantId 租户id
     * @param username 用户名
     * @return {@link UserRoleMenuFuncDtlDTO}
     */
    public UserRoleMenuFuncDtlDTO getUserDtl(String tenantId, String username) {
        User user = userRepository.getByUsername(tenantId, username);
        UserRoleMenuFuncDtlDTO userRoleMenuFuncDtlDTO = UserAssembler.INSTANCE.entityToDtl(user);
        if (userRoleMenuFuncDtlDTO == null) {
            return null;
        }
        List<UserRole> userRoles = userRoleRepository.listByUserId(user.getId());
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        RoleMenuFuncDtlDTO roleMenuFuncDtl = mergeMenuByRoleIds(roleIds, false);
        if (roleMenuFuncDtl == null) {
            return null;
        }
        userRoleMenuFuncDtlDTO.setRoleIds(roleMenuFuncDtl.getRoleIds());
        userRoleMenuFuncDtlDTO.setRoleCodes(roleMenuFuncDtl.getRoleCodes());
        userRoleMenuFuncDtlDTO.setMenuIds(roleMenuFuncDtl.getMenuIds());
        userRoleMenuFuncDtlDTO.setRoles(roleMenuFuncDtl.getRoles());
        userRoleMenuFuncDtlDTO.setMenus(roleMenuFuncDtl.getMenus());
        return userRoleMenuFuncDtlDTO;
    }


    /**
     * 合并菜单按角色id
     *
     * @param roleIds 角色id列表
     * @param showAll 显示所有信息，匹配到的信息，设置匹配字段checkbox=true
     * @return {@link RoleMenuFuncDtlDTO}
     */
    public RoleMenuFuncDtlDTO mergeMenuByRoleIds(List<Long> roleIds, boolean showAll) {
        // @formatter:off
        if (CollUtil.isEmpty(roleIds)) {
            return null;
        }
        RoleMenuFuncDtlDTO roleMenuFuncDtlDTO = new RoleMenuFuncDtlDTO();
        // 1.根据角色id列表查询角色信息、菜单信息、角色菜单信息、角色功能号信息、菜单功能号信息
        // 根据角色id列表 查询角色信息
        List<Role> roles = roleRepository.listByIds(roleIds);
        // 根据角色id列表 查询角色菜单关联信息
        List<RoleMenu> roleMenus = roleMenuRepository.listByRoleIds(roleIds);
        // 合并的角色后所有的菜单
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        // 根据角色id列表 查询角色功能号关联信息
        List<RoleFunc> roleFuncs = roleFuncRepository.listByRoleIds(roleIds);
        // 菜单功能号信息
        Map<Long, List<Func>> menuFuncMap = showAll ? menuFuncRepository.getMenuFuncMap() : menuFuncRepository.getMenuFuncMapByMenuIds(menuIds);
        // 菜单信息
        List<Menu> menus = showAll ? menuRepository.list() : menuRepository.listByIds(menuIds);
        // 合并角色后的菜单功能号信息 根据菜单id分组，集合为功能号id列表
        Map<Long, List<Long>> menuFuncGroup = roleFuncs
            .stream()
            .collect(Collectors.groupingBy(RoleFunc::getMenuId, Collectors.mapping(RoleFunc::getFuncId, Collectors.toList())));
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
        // 菜单信息过滤规则
        Predicate<MenuFuncDtlDTO> menuFilter = x -> true;
        // 菜单信息操作
        Consumer<MenuFuncDtlDTO> menuPeek = x -> x.setCheckbox(true);
        // 功能号信息过滤规则
        BiPredicate<Long, FuncDTO> funcFilter = (a, b) -> true;
        // 功能号信息操作
        BiConsumer<Long, FuncDTO> funcPeek = (a, b) -> b.setCheckbox(true);
        // 如果展示全部字段，匹配的数据进行填充checkbox=true
        if (showAll) {
            menuFilter = x -> true;
            menuPeek = x -> x.setCheckbox(menuIds.contains(x.getId()));
            funcFilter = (a, b) -> true;
            funcPeek = (a, b) -> b.setCheckbox(containFunc(a, b, menuFuncGroup));
        }
        // 根据菜单信息，填充功能号信息
        List<MenuFuncDtlDTO> menuFuncDtls = getMenuFuncDtls(menus, menuFilter, menuPeek);
        // 菜单信息规整为树结构
        BiPredicate<Long,FuncDTO> finalFuncFilter = funcFilter;
        BiConsumer<Long,FuncDTO> finalFuncPeek = funcPeek;
        List<MenuFuncDtlDTO> tree = TreeUtil.tree(menuFuncDtls, 0L, x -> setFuncs(menuFuncMap, x, finalFuncFilter, finalFuncPeek));
        roleMenuFuncDtlDTO.setRoleIds(roleIds);
        roleMenuFuncDtlDTO.setMenus(tree);
        roleMenuFuncDtlDTO.setMenuIds(menuIds);
        roleMenuFuncDtlDTO.setRoleCodes(roleCodes);
        roleMenuFuncDtlDTO.setRoles(roleDtos);
        return roleMenuFuncDtlDTO;
        // @formatter:on
    }

    private boolean containFunc(Long menuId, FuncDTO funcDto, Map<Long, List<Long>> menuFuncGroup) {
        List<Long> longs = menuFuncGroup.get(menuId);
        if (CollUtil.isEmpty(longs)) {
            return false;
        }
        return longs.contains(funcDto.getId());
    }

    // @formatter:off

    /**
     * 根据菜单信息，填充功能号信息
     *
     * @param menus 菜单
     * @param filter 过滤规则
     * @param peek 执行规则
     * @return {@link List}<{@link MenuFuncDtlDTO}>
     */
    public List<MenuFuncDtlDTO> getMenuFuncDtls(List<Menu> menus, Predicate<MenuFuncDtlDTO> filter, Consumer<MenuFuncDtlDTO> peek) {
        return menus
            .stream()
            .map(MenuAssembler.INSTANCE::entityToDtlDto)
            .filter(filter)
            .peek(peek)
            .sorted(Comparator.comparing(MenuFuncDtlDTO::getParentId).thenComparing(Comparator.nullsLast(Comparator.comparing(MenuFuncDtlDTO::getSort))))
            .collect(Collectors.toList());
    }
    // @formatter:on

    // @formatter:off

    /**
     * 菜单信息填充功能号
     *
     * @param menuFuncMap 菜单功能号map
     * @param menuFuncDtl 菜单功能号详细信息
     * @param filter 过滤规则
     * @param peek 执行规则
     */
    public void setFuncs(
        Map<Long, List<Func>> menuFuncMap,
        MenuFuncDtlDTO menuFuncDtl,
        BiPredicate<Long, FuncDTO> filter,
        BiConsumer<Long, FuncDTO> peek
    ) {
        Long menuId = menuFuncDtl.getId();
        List<Func> funcs = Optional.ofNullable(menuFuncMap.get(menuId)).orElseGet(ArrayList::new);
        List<Long> funcIds = new ArrayList<>();
        List<String> funcCodes = new ArrayList<>();
        List<FuncDTO> funcDtos = funcs
            .stream()
            .peek(func-> collectFuncInfo(funcIds, funcCodes, func))
            .map(FuncAssembler.INSTANCE::entityToDto)
            .filter(x-> filter.test(menuId, x))
            .peek(x-> peek.accept(menuId, x))
            .collect(Collectors.toList());
        menuFuncDtl.setFuncs(funcDtos);
        menuFuncDtl.setFuncIds(funcIds);
        menuFuncDtl.setFuncCodes(funcCodes);
    }

    // @formatter:on

    /**
     * 收集函功能号字段信息
     *
     * @param funcIds 功能号id列表
     * @param funcCodes 功能号code列表
     * @param func 功能号信息
     */
    private void collectFuncInfo(List<Long> funcIds, List<String> funcCodes, Func func) {
        funcIds.add(func.getId());
        funcCodes.add(func.getCode());
    }

}
