# 1. Sử dụng OpenJDK 17 chính thức
FROM eclipse-temurin:17-jdk-alpine

# 2. Thiết lập thư mục làm việc trong container
WORKDIR /app

# 3. Copy file JAR từ project vào container
COPY target/big-ex-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

# 4. Chạy ứng dụng Spring Boot
CMD ["java", "-jar", "app.jar"]
