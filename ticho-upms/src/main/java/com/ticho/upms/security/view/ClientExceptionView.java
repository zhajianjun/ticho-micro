package com.ticho.upms.security.view;

import com.ticho.boot.view.core.HttpErrCode;
import com.ticho.boot.view.core.Result;
import com.ticho.boot.web.util.JsonUtil;
import com.ticho.upms.security.filter.CustomClientCredentialsTokenEndpointFilter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * client错误返回视图修改
 * <p>
 *     1.需要在权限服务的AuthorizationServerSecurityConfigurer 添加CustomClientCredentialsTokenEndpointFilter过滤器，并添加返回视图才可
 *     2..allowFormAuthenticationForClients()这个配置要去掉
 * </p>
 *
 * @see CustomClientCredentialsTokenEndpointFilter
 * @author zhajianjun
 * @date 2021-08-22 14:57
 */
public class ClientExceptionView implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        Result<String> fail = Result.of(HttpErrCode.TOKEN_INVALID, e.getMessage());
        fail.setData(req.getRequestURI());
        String result = JsonUtil.toJsonString(fail);
        res.getWriter().write(result);
    }
}
