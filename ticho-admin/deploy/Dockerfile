FROM java:openjdk-8-jre-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "$JVM_OPTS","-Dserver.port=8080", "/app.jar"]