package com.ticho.upms.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户信息
 *
 * @author zhajianjun
 * @date 2021-10-24 22:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户")
@TableName("sys_user")
public class User extends Model<User> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id", position = 10)
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "账户", position = 20)
    private String username;

    @ApiModelProperty(value = "密码", position = 30)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ApiModelProperty(value = "昵称", position = 40)
    private String nickname;

    @ApiModelProperty(value = "真实姓名", position = 50)
    private String realname;

    @ApiModelProperty(value = "身份证号", position = 60)
    private String idcard;

    @ApiModelProperty(value = "0，男；1，女", position = 70)
    private Integer sex;

    @ApiModelProperty(value = "年龄", position = 80)
    private Integer age;

    @ApiModelProperty(value = "出生日期", position = 90)
    private String birthday;

    @ApiModelProperty(value = "家庭住址", position = 100)
    private String address;

    @ApiModelProperty(value = "学历", position = 110)
    private String education;

    @ApiModelProperty(value = "邮箱", position = 120)
    private String email;

    @ApiModelProperty(value = "QQ号码", position = 130)
    private Integer qq;

    @ApiModelProperty(value = "微信号码", position = 140)
    private String wechat;

    @ApiModelProperty(value = "手机号码", position = 150)
    private String mobile;

    @ApiModelProperty(value = "头像地址", position = 160)
    private String photo;

    @ApiModelProperty(value = "最后登录ip地址", position = 170)
    private String lastIp;

    @ApiModelProperty(value = "最后登录时间", position = 180)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastTime;

    @ApiModelProperty(value = "用户状态。1-正常,2-已失效,3-已被锁定,4-已过期", position = 181)
    private Integer status;

    @ApiModelProperty(value = "备注", position = 190)
    private String remark;

    @ApiModelProperty(value = "创建人", position = 200)
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建时间", position = 210)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)   
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人", position = 220)
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "修改时间", position = 230)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标识。0-未删除,1-已删除", position = 240)
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;

}
