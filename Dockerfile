# 1. JDK
FROM eclipse-temurin:17-jre-alpine
# 2. work directory inside the container
WORKDIR /app
# 3. copy jar file
COPY target/localchat-*.jar app.jar
# 4. expose port
EXPOSE 8080
# 5. run app
ENTRYPOINT ["java", "-jar", "app.jar"]
