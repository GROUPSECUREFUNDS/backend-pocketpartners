# Etapa de compilación: usa Maven con Java 22
FROM maven:3.9.5-eclipse-temurin-22 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución: usa Java 22 (misma versión que la de build)
FROM eclipse-temurin:22-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]