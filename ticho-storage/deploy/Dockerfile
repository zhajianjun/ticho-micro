FROM java:8u92-jre-alpine
VOLUME /tmp
COPY ticho-storage-app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]