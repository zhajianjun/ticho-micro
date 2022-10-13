package com.ticho.upms.domain.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.util.Assert;
import com.ticho.boot.web.util.valid.ValidUtil;
import com.ticho.upms.application.service.UserService;
import com.ticho.upms.domain.repository.TenantRepository;
import com.ticho.upms.domain.repository.UserRepository;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.interfaces.assembler.UserAssembler;
import com.ticho.upms.interfaces.dto.SignUpDTO;
import com.ticho.upms.interfaces.dto.UpmsUserDTO;
import com.ticho.upms.interfaces.dto.UserDTO;
import com.ticho.upms.interfaces.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
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
    public void save(UserDTO userDTO) {
        User user = UserAssembler.INSTANCE.dtoToEntity(userDTO);
        Assert.isTrue(userRepository.save(user), BizErrCode.FAIL, "保存失败");
    }

    @Override
    public void removeById(Serializable id) {
        Assert.isTrue(userRepository.removeById(id), BizErrCode.FAIL, "删除失败");
    }

    @Override
    public void updateById(UserDTO userDTO) {
        User user = UserAssembler.INSTANCE.dtoToEntity(userDTO);
        Assert.isTrue(userRepository.updateById(user), BizErrCode.FAIL, "修改失败");
    }

    @Override
    public UpmsUserDTO getByUsername(String tenantId, String username) {
        User user = userRepository.getByUsername(tenantId, username);
        UpmsUserDTO securityUser = UserAssembler.INSTANCE.userToUmpsUsrDto(user);
        setAuthorities(securityUser);
        return securityUser;
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

    @Override
    public void signUp(SignUpDTO signUpDTO) {
        ValidUtil.valid(signUpDTO);
        String tenantId = signUpDTO.getTenantId();
        String username = signUpDTO.getUsername();
        String password = signUpDTO.getPassword();
        boolean exists = tenantRepository.exists(tenantId);
        Assert.isTrue(exists, BizErrCode.FAIL, "租户不存在或者状态异常");
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    private void setAuthorities(UpmsUserDTO userUpdDto) {
        if (userUpdDto == null) {
            return;
        }
        userUpdDto.setRoleIds(Collections.singletonList("admin"));
    }
}
