package com.ticho.gateway.filter;

import lombok.Data;

/**
 *
 *
 * @author zhajianjun
 * @date 2023-01-06 16:13
 */
@Data
public class GatewayLog {
    /**请求来源**/
    private String origin;

    /**访问实例*/
    private String targetServer;

    /**请求路径*/
    private String requestPath;

    /**请求方法*/
    private String requestMethod;

    /**协议 */
    private String schema;

    /**请求类型 */
    private String requestContentType;

    /**请求头 */
    private Object headers;

    /**请求体*/
    private Object requestBody;

    /**响应体*/
    private Object responseData;

    /**请求ip*/
    private String ip;

    /**IP所属城市*/
    private String city;

    /**开始时间*/
    private Long startTime;

    /**结束时间*/
    private Long endTime;

    /**请求时间*/
    private String requestTime;

    /**响应时间*/
    private String responseTime;

    /**执行时间*/
    private long executeTime;

    /**路由配置*/
    private String routeConfig;

    /**响应状态*/
    private String status;
}
