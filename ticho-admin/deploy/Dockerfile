FROM java:openjdk-8-jre-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENV JVM_OPTS="-server -Xmn64m -Xms128m -Xmx256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"
ENTRYPOINT ["exec", "java", "-jar", "$JVM_OPTS","-Dserver.port=8080", "/app.jar"]