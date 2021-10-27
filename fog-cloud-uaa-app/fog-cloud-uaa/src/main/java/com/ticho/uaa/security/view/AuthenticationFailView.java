package com.ticho.uaa.security.view;

import com.ticho.core.mvc.util.JsonUtils;
import com.ticho.core.mvc.view.HttpResultCode;
import com.ticho.core.mvc.view.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Token失效返回视图
 *
 * <p>
 *     只要带着token并且异常，一般都是token失效导致异常的返回视图
 * </p>
 *
 * @author AdoroTutto
 * @date 2021-08-20 12:24 下午
 */
public class AuthenticationFailView implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        Result<String> fail = Result.of(HttpResultCode.TOKEN_INVALID);
        fail.setData(req.getRequestURI());
        String result = JsonUtils.toJsonString(fail);
        PrintWriter writer = res.getWriter();
        writer.write(result);
        writer.close();
    }
}
