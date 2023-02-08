package com.ticho.email.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.mail.component.MailContent;
import com.ticho.boot.mail.component.MailTemplate;
import com.ticho.boot.web.annotation.View;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邮件控制器
 *
 * @author zhajianjun
 * @date 2022-09-02 16:36
 */
@RestController
@RequestMapping("email")
@Api(tags = "邮件")
@View
@ApiSort(10)
public class EmailController {

    @Autowired
    private MailTemplate mailTemplate;

    @PreAuthorize("@pm.hasPerms('email:email:send')")
    @ApiOperation(value = "邮件发送")
    @ApiOperationSupport(order = 10)
    @PostMapping("send")
    public void sendSimpleMail(MailContent mailContent) {
        mailTemplate.sendSimpleMail(mailContent);
    }

}
