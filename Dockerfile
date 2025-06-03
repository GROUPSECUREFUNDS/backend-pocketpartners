# Etapa de compilación: usa Maven con Java 22
FROM maven:3-eclipse-temurin-22-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución: usa Java 22 (misma versión que la de build)
FROM openjdk:22-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto 8080 para la aplicación
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]