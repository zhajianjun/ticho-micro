package com.ticho.upms.interfaces.dto;

import com.ticho.boot.view.core.TichoSecurityUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-26 10:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SecurityUser extends TichoSecurityUser {

    @ApiModelProperty(value = "用户状态。1-正常,2-已失效,3-已被锁定,4-已过期", position = 40)
    private Integer status = 2;

    @Override
    public String toString(){
        return super.toString();
    }

}
