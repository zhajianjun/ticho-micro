package com.ticho.upms.security.view;

import com.ticho.boot.view.core.HttpErrCode;
import com.ticho.boot.view.core.Result;
import com.ticho.boot.web.util.JsonUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功,权限不足返回视图
 *
 * @author zhajianjun
 * @date 2021-08-19 23:52
 */
public class PermissionDeniedView implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException e) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        Result<String> fail = Result.of(HttpErrCode.ACCESS_DENIED);
        fail.setData(req.getRequestURI());
        String result = JsonUtil.toJsonString(fail);
        res.getWriter().write(result);
    }
}
