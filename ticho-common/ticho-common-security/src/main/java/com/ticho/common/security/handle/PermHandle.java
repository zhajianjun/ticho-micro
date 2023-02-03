package com.ticho.common.security.handle;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.ticho.boot.redis.util.RedisUtil;
import com.ticho.boot.web.util.SpringContext;
import com.ticho.common.security.constant.SecurityConst;
import com.ticho.common.security.dto.PermDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限处理
 *
 * @author zhajianjun
 * @date 2023-02-03 15:36
 */
public class PermHandle {

    private final RedisUtil<String, String> redisUtil;

    public PermHandle(RedisUtil<String, String> redisUtil) {
        this.redisUtil = redisUtil;
    }

    public List<PermDTO> pushCurrentAppPerms() {
        List<PermDTO> perms = listCurrentAppPerms();
        if (CollUtil.isEmpty(perms)) {
            return Collections.emptyList();
        }
        Map<String, String> map = perms.stream().collect(Collectors.toMap(PermDTO::getCode, PermDTO::getName));
        redisUtil.hPutAll(SecurityConst.MICRO_REDIS_ALL_PERMS, map);
        return perms;
    }

    public List<PermDTO> listAllAppPerms() {
        Map<String, String> permsStr = redisUtil.hGetAll(SecurityConst.MICRO_REDIS_ALL_PERMS);
        Set<Map.Entry<String, String>> entries = permsStr.entrySet();
        List<PermDTO> perms = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries) {
            PermDTO permDTO = new PermDTO();
            permDTO.setCode(entry.getKey());
            permDTO.setName(entry.getValue());
            perms.add(permDTO);
        }
        return perms;
    }

    /**
     * 获取当前应用的全部权限标识
     *
     * @return {@link List}<{@link PermDTO}>
     */
    public List<PermDTO> listCurrentAppPerms() {
        // @formatter:off
        RequestMappingHandlerMapping mapping = SpringContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        return map.values()
            .stream()
            .map(this::getFunc)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private PermDTO getFunc(HandlerMethod handlerMethod) {
        PreAuthorize preAuthorize = handlerMethod.getMethodAnnotation(PreAuthorize.class);
        if (preAuthorize == null) {
            return null;
        }
        String value = preAuthorize.value();
        if (StrUtil.isBlank(value)) {
            return null;
        }
        int start = value.indexOf("'") + 1;
        int end = value.lastIndexOf("'");
        value = value.substring(start, end);
        ApiOperation apiOperation = handlerMethod.getMethodAnnotation(ApiOperation.class);
        String name = handlerMethod.getBeanType().toString();
        PermDTO perm = new PermDTO();
        perm.setCode(value);
        perm.setName(apiOperation == null ? name : apiOperation.value());
        return perm;
    }

}
