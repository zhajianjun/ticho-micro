package com.ticho.uaa.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * oauth2客户端信息
 *
 * @author zhajianjun
 * @date 2021-10-30 23:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "oauth2客户端信息")
@TableName("oauth_client_details")
public class OauthClientDetails extends Model<OauthClientDetails> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户端id", position = 10)
    @TableId(value = "client_id", type = IdType.INPUT)
    private String clientId;

    @ApiModelProperty(value = "客户端密码", position = 20)
    private String clientSecret;

    @ApiModelProperty(value = "客户端资源", position = 30)
    private String resourceIds;

    @ApiModelProperty(value = "权限范围，比如读写权限，比如移动端还是web端权限", position = 40)
    private String scope;

    @ApiModelProperty(value = "授权模式。授权码模式:authorization_code，密码模式:password，刷新token: refresh_token，隐式模式: implicit，客户端模式: client_credentials。多个用逗号分隔", position = 50)
    private String authorizedGrantTypes;

    @ApiModelProperty(value = "客户端重定向uri，authorization_code和implicit需要该值进行校验，注册时填写", position = 60)
    private String webServerRedirectUri;

    @ApiModelProperty(value = "指定用户的权限范围，如果授权的过程需要用户登陆，该字段不生效，implicit和client_credentials需要", position = 70)
    private String authorities;

    @ApiModelProperty(value = "access_token的有效时间(秒)", position = 80)
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "refresh_token有效期(秒)", position = 90)
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "自定义信息。必须是json格式", position = 100)
    private String additionalInformation;

    @ApiModelProperty(value = "默认false，适用于authorization_code模式，设置用户是否自动approval操作，设置true跳过用户确认授权操作页面，直接跳到redirect_uri", position = 110)
    private String autoapprove;

    @ApiModelProperty(value = "备注", position = 120)
    private String remark;

    @ApiModelProperty(value = "创建人", position = 130)
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建时间", position = 140)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人", position = 150)
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "修改时间", position = 160)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标识。0-未删除,1-已删除", position = 170)
    @TableField(fill = FieldFill.INSERT)
    private Boolean isDeleted;
}
