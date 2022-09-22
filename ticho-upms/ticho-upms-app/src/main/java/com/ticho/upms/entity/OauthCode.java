package com.ticho.upms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * oauth2授权码信息
 *
 * @author zhajianjun
 * @date 2021-10-30 23:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "oauth2授权码信息")
@TableName("oauth_code")
public class OauthCode extends Model<OauthCode> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "授权码", position = 10)
    @TableId(value = "`code`", type = IdType.INPUT)
    private String code;

    @ApiModelProperty(value = "权限详细信息", position = 20)
    private byte[] authentication;

}
