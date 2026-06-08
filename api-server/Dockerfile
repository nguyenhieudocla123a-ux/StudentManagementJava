# Bước 1: Build dự án bằng Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy file cấu hình và source code
COPY pom.xml .
COPY src ./src

# Build JAR file
RUN mvn clean package -DskipTests

# Bước 2: Chạy ứng dụng
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy JAR từ build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
