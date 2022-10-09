package com.ticho.upms.interfaces.assembler;

import com.ticho.upms.interfaces.dto.UpmsUserDTO;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.interfaces.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author zhajianjun
 * @date 2021-10-27 23:58
 */
@Mapper
public interface UserAssembler {
    UserAssembler INSTANCE = Mappers.getMapper(UserAssembler.class);


    /**
     * 用户dto
     *
     * @param user 用户
     * @return {@link UpmsUserDTO}
     */
    @Mapping(target = "roleIds", ignore = true)
    @Mapping(target = "deptIds", ignore = true)
    UpmsUserDTO userToDto(User user);


    /**
     * 用户信息
     *
     * @param userDTO 用户更新对象
     * @return {@link User}
     */
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "lastTime", ignore = true)
    @Mapping(target = "lastIp", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    User updToUser(UserDTO userDTO);
}
