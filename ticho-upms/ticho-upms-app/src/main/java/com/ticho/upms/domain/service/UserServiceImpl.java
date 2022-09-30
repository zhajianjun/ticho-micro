package com.ticho.upms.domain.service;

import com.ticho.upms.application.service.UserService;
import com.ticho.upms.domain.repository.UserRepository;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.interfaces.assembler.UserAssembler;
import com.ticho.upms.interfaces.dto.UserDTO;
import com.ticho.upms.interfaces.dto.UserUpdDTO;
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
    public void saveUser(UserUpdDTO userUpdDTO) {
        User user = UserAssembler.INSTANCE.updToUser(userUpdDTO);
        userRepository.save(user);
    }

    @Override
    public UserDTO getByUsername(String username) {
        User user = userRepository.getByUsername(username);
        UserDTO securityUser = UserAssembler.INSTANCE.userToDto(user);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public UserDTO getByMobile(String mobile) {
        User user = userRepository.getByMobile(mobile);
        UserDTO securityUser = UserAssembler.INSTANCE.userToDto(user);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public UserDTO getByEmail(String email) {
        User user = userRepository.getByEmail(email);
        UserDTO securityUser = UserAssembler.INSTANCE.userToDto(user);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public UserDTO getByWechat(String wechat) {
        User user = userRepository.getByWechat(wechat);
        UserDTO securityUser = UserAssembler.INSTANCE.userToDto(user);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public UserDTO getByQq(String qq) {
        User user = userRepository.getByQq(qq);
        UserDTO securityUser = UserAssembler.INSTANCE.userToDto(user);
        setAuthorities(securityUser);
        return securityUser;
    }

    private void setAuthorities(UserDTO userUpdDto) {
        if (userUpdDto == null) {
            return;
        }
        userUpdDto.setRoleIds(Collections.singletonList("admin"));
    }


}
