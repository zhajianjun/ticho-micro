package com.ticho.uaa.security.view;

import com.ticho.core.mvc.util.JsonUtils;
import com.ticho.core.mvc.view.HttpResultCode;
import com.ticho.core.mvc.view.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功,权限不足返回视图
 *
 * @author AdoroTutto
 * @date 2021-08-19 23:52
 */
public class PermissionDeniedView implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException e) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        Result<String> fail = Result.of(HttpResultCode.ACCESS_DENIED);
        fail.setData(req.getRequestURI());
        String result = JsonUtils.toJsonString(fail);
        res.getWriter().write(result);
    }
}
