package com.ticho.upms.domain.service;

import com.ticho.upms.application.service.UserService;
import com.ticho.upms.domain.repository.UserRepository;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.interfaces.assembler.UserAssembler;
import com.ticho.upms.interfaces.dto.UpmsUserDTO;
import com.ticho.upms.interfaces.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 用户 服务实现
 *
 * @author zhajianjun
 * @date 2021-10-24 22:12
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(UserDTO userDTO) {
        User user = UserAssembler.INSTANCE.updToUser(userDTO);
        userRepository.save(user);
    }

    @Override
    public UpmsUserDTO getByUsername(String username) {
        User user = userRepository.getByUsername(username);
        UpmsUserDTO securityUser = UserAssembler.INSTANCE.userToDto(user);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public UpmsUserDTO getByMobile(String mobile) {
        User user = userRepository.getByMobile(mobile);
        UpmsUserDTO securityUser = UserAssembler.INSTANCE.userToDto(user);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public UpmsUserDTO getByEmail(String email) {
        User user = userRepository.getByEmail(email);
        UpmsUserDTO securityUser = UserAssembler.INSTANCE.userToDto(user);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public UpmsUserDTO getByWechat(String wechat) {
        User user = userRepository.getByWechat(wechat);
        UpmsUserDTO securityUser = UserAssembler.INSTANCE.userToDto(user);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public UpmsUserDTO getByQq(String qq) {
        User user = userRepository.getByQq(qq);
        UpmsUserDTO securityUser = UserAssembler.INSTANCE.userToDto(user);
        setAuthorities(securityUser);
        return securityUser;
    }

    private void setAuthorities(UpmsUserDTO userUpdDto) {
        if (userUpdDto == null) {
            return;
        }
        userUpdDto.setRoleIds(Collections.singletonList("admin"));
    }


}
