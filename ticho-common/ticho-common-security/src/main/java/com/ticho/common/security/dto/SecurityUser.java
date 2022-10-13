package com.ticho.common.security.dto;

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

    @ApiModelProperty(value = "租户ID", position = 20)
    private String tenantId;

    @ApiModelProperty(value = "账户", position = 10)
    private String username;

    @ApiModelProperty(value = "用户状态;1-正常,2-未激活,3-已锁定,4-已注销", position = 40)
    private Integer status = 2;

    @Override
    public String toString(){
        return super.toString();
    }

}
