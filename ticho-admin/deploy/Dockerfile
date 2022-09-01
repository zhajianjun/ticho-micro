FROM java:openjdk-8-jre-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar", "-server -Xms$JVM_XMS -Xmx$JVM_XMX -Xmn$JVM_XMN -XX:MetaspaceSize=$JVM_MS -XX:MaxMetaspaceSize=$JVM_MMS", "-Dserver.port=8080","/app.jar"]