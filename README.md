## Spring Cloud组件说明

### Spring Cloud Alibaba Nacos
#### 功能
1. 服务注册中心
2. 分布式配置中心

### Spring Cloud Sleuth
#### 简述
1. Sleuth是Spring Cloud的组件之一，它为Spring Cloud实现了一种分布式追踪解决方案，兼容Zipkin，HTrace和其他基于日志的追踪系统，例如 ELK（Elasticsearch 、Logstash、 Kibana）。

#### 功能

1. 链路追踪，比如在header中自定义一些参数，会在请求中链路中一直被传递。

### Spring Cloud Zipkin Server
#### 功能
1. 分布式链路追踪服务器,存储和ui展示
#### 注意
1. zipkin也可以单独从官网下载可执行jar

## 服务说明

### fog-cloud-admin
1. 简述：

### fog-cloud-core
1. 简述：

#### 

### fog-starter-gateway-app
1. 简述：

### fog-cloud-storage-app
1. 简述：

### fog-cloud-uaa-app
1. 简述：

# 注意事项
1. nacos配置中心的配置必须放在bootstrap中，且必须设置应用名，否则nacos配置无法动态刷新。注册中心不强求。

2. springboot配置文件使用pom中的参数作为配置，需要在pom中添加配置，如下

   ```xml
   <build>
       <resources>
           <resource>
               <directory>src/main/resources</directory>
               <filtering>true</filtering>
           </resource>
       </resources>
   </build>
   ```
