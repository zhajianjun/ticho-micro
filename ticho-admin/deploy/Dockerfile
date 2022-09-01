FROM java:openjdk-8-jre-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENV JVM_OPTS="-server -Xms512m -Xmx512m -Xmn170m"
ENTRYPOINT ["java", "$JVM_OPTS", "-jar","-Dserver.port=8080", "/app.jar"]