package com.ticho.common.security.filter;

import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.Result;
import com.ticho.boot.web.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 认证成功,权限不足返回视图
 *
 * @author zhajianjun
 * @date 2022-09-23 17:44:39
 */
@Component
@Slf4j
public class RewriteAccessDenyFilter implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            AccessDeniedException e) throws IOException {
        Result<String> retResult = Result.of(BizErrCode.FAIL, "抱歉，您没有访问该接口的权限");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(JsonUtil.toJsonString(retResult));
        log.warn("RewriteAccessDenyFilter.handle-end");
    }
}
