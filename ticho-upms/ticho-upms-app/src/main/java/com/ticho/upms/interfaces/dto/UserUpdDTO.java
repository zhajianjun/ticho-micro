package com.ticho.upms.interfaces.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户更新对象
 *
 * @author zhajianjun
 * @date 2022-09-30 16:45
 */
@Data
@ApiModel(value = "用户更新对象")
public class UserUpdDTO {

    @ApiModelProperty(value = "主键id", position = 10)
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "账户", position = 20)
    private String username;

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

    @ApiModelProperty(value = "用户状态。1-正常,2-已失效,3-已被锁定,4-已过期", position = 181)
    private Integer status;

}
