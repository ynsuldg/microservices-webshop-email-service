FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/email-service-projekt2-1.0.0.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
