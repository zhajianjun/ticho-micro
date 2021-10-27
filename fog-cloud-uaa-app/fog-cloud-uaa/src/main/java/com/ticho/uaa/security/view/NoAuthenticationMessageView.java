package com.ticho.uaa.security.view;

import com.ticho.core.mvc.util.JsonUtils;
import com.ticho.core.mvc.view.HttpResultCode;
import com.ticho.core.mvc.view.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无认证信息返回视图
 *
 * <p>
 *     未放行的接口header中没有token信息时，异常返回视图
 * </p>
 *
 * @author AdoroTutto
 * @date 2021-08-20 12:32 下午
 */
public class NoAuthenticationMessageView implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        Result<String> fail = Result.of(HttpResultCode.NOT_LOGIN);
        fail.setData(req.getRequestURI());
        String result = JsonUtils.toJsonString(fail);
        res.getWriter().write(result);
    }
}
