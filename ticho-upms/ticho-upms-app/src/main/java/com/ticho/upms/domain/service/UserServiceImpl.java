package com.ticho.upms.domain.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.util.Assert;
import com.ticho.boot.web.util.valid.ValidGroup;
import com.ticho.boot.web.util.valid.ValidUtil;
import com.ticho.common.security.dto.SecurityUser;
import com.ticho.common.security.util.SecurityUtil;
import com.ticho.upms.application.service.UserService;
import com.ticho.upms.domain.repository.TenantRepository;
import com.ticho.upms.domain.repository.UserRepository;
import com.ticho.upms.infrastructure.core.enums.TenantStatus;
import com.ticho.upms.infrastructure.core.enums.UserStatus;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.interfaces.assembler.UserAssembler;
import com.ticho.upms.interfaces.dto.UserAccountDTO;
import com.ticho.upms.interfaces.dto.UserDTO;
import com.ticho.upms.interfaces.dto.UserSignUpDTO;
import com.ticho.upms.interfaces.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户信息 服务实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void signUp(UserSignUpDTO userSignUpDTO) {
        ValidUtil.valid(userSignUpDTO);
        String tenantId = userSignUpDTO.getTenantId();
        String username = userSignUpDTO.getUsername();
        String password = userSignUpDTO.getPassword();
        boolean exists = tenantRepository.exists(tenantId, Collections.singletonList(TenantStatus.NORMAL.code()));
        Assert.isTrue(exists, BizErrCode.FAIL, "租户不存在或者状态异常");
        User user = new User();
        user.setTenantId(tenantId);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setStatus(UserStatus.NOT_ACTIVE.code());
        UserAccountDTO accountDTO = UserAssembler.INSTANCE.entityToAccount(user);
        preCheckRepeatUser(accountDTO, null);
        Assert.isTrue(userRepository.save(user), BizErrCode.FAIL, "注册失败");
    }

    @Override
    public void confirm(String username) {
        Assert.isNotBlank(username, BizErrCode.PARAM_ERROR, "账户不能为空");
        SecurityUser securityUser = SecurityUtil.getCurrentUser();
        String tenantId = securityUser.getTenantId();
        User user = userRepository.getByUsername(tenantId, username);
        User updateEntity = new User();
        updateEntity.setId(user.getId());
        updateEntity.setStatus(UserStatus.NORMAL.code());
        Assert.isTrue(userRepository.updateById(updateEntity), BizErrCode.FAIL, "确认失败");
    }

    @Override
    public void save(UserDTO userDTO) {
        ValidUtil.valid(userDTO, ValidGroup.Add.class);
        String password = userDTO.getPassword();
        userDTO.setPassword(passwordEncoder.encode(password));
        User user = UserAssembler.INSTANCE.dtoToEntity(userDTO);
        UserAccountDTO accountDTO = UserAssembler.INSTANCE.entityToAccount(user);
        preCheckRepeatUser(accountDTO, null);
        Assert.isTrue(userRepository.save(user), BizErrCode.FAIL, "保存失败");
    }

    @Override
    public void removeById(Serializable id) {
        Assert.isTrue(userRepository.removeById(id), BizErrCode.FAIL, "删除失败");
    }

    @Override
    public void updateById(UserDTO userDTO) {
        ValidUtil.valid(userDTO, ValidGroup.Upd.class);
        userDTO.setPassword(null);
        User user = UserAssembler.INSTANCE.dtoToEntity(userDTO);
        UserAccountDTO accountDTO = UserAssembler.INSTANCE.entityToAccount(user);
        preCheckRepeatUser(accountDTO, userDTO.getId());
        Assert.isTrue(userRepository.updateById(user), BizErrCode.FAIL, "修改失败");
    }

    @Override
    public UserDTO getById(Serializable id) {
        User user = userRepository.getById(id);
        return UserAssembler.INSTANCE.entityToDto(user);
    }

    @Override
    public PageResult<UserDTO> page(UserQuery query) {
        // @formatter:off
        query.checkPage();
        Page<User> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        userRepository.list(query);
        List<UserDTO> userDTOs = page.getResult()
            .stream()
            .map(UserAssembler.INSTANCE::entityToDto)
            .collect(Collectors.toList());
        return new PageResult<>(page.getPageNum(), page.getPageSize(), page.getTotal(), userDTOs);
        // @formatter:on
    }

    /**
     * 保存或者修改用户信息重复数据判断，用户名称、邮箱、手机号保证其唯一性
     *
     * @param userAccountDTO 用户登录账号信息
     */
    private void preCheckRepeatUser(UserAccountDTO userAccountDTO, Long updateId) {
        String username = userAccountDTO.getUsername();
        String email = userAccountDTO.getEmail();
        String mobile = userAccountDTO.getMobile();
        List<User> users = userRepository.getByAccount(userAccountDTO);
        boolean isSaveOrUpdate = Objects.nonNull(updateId);
        for (User item : users) {
            Long itemId = item.getId();
            if (!isSaveOrUpdate && Objects.equals(updateId, itemId)) {
                continue;
            }
            String itemUsername = item.getUsername();
            String itemMobile = item.getMobile();
            String itemEmail = item.getEmail();
            // 用户名重复判断
            Assert.isTrue(!Objects.equals(username, itemUsername), BizErrCode.FAIL, "该用户名已经存在");
            // 手机号码重复判断
            Assert.isTrue(!Objects.equals(mobile, itemMobile), BizErrCode.FAIL, "该手机号已经存在");
            // 邮箱重复判断
            Assert.isTrue(!Objects.equals(email, itemEmail), BizErrCode.FAIL, "该邮箱已经存在");
        }
    }

}
