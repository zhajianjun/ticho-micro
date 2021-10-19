package com.ticho.core.mvc.view;

import com.ticho.core.mvc.util.JsonUtils;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * 自定义404接口
 * FIXME 404 好像没用
 *
 * @author AdoroTutto
 * @date 2021-10-16 20:24
 */
@Component
@Deprecated
public class CustomerErrorController extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Object path = webRequest.getAttribute("javax.servlet.error.request_uri", 0);
        Object code = webRequest.getAttribute("javax.servlet.error.status_code", 0);
        Object msg = webRequest.getAttribute("javax.servlet.error.message", 0);
        Result<Object> result = new Result<>();
        result.setCode(code == null ? null : (int) code);
        result.setMsg(msg == null ? null : msg.toString());
        result.setData(path);
        return JsonUtils.toMap(result);
    }

}
