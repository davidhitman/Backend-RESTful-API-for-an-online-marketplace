FROM eclipse-temurin:23

WORKDIR /app

COPY target/*.jar challenge.jar

ENTRYPOINT ["java", "-jar", "challenge.jar"]

