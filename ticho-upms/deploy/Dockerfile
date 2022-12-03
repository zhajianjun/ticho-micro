FROM java:8u92-jre-alpine
VOLUME /tmp
COPY ticho-upms-app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]