# Етап складання
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Базовий контейнер для запуску додатку
FROM openjdk:17.0.2-jdk-slim

# Встановлюємо необхідні залежності для Tesseract
RUN apt-get update && apt-get install -y \
    tesseract-ocr \
    && rm -rf /var/lib/apt/lists/*

# Копіюємо зібраний jar файл
COPY --from=build /target/highlight-server-spring-0.0.1-SNAPSHOT.jar highlightserverspring.jar

# Створюємо директорію для tessdata
RUN mkdir -p /usr/share/tesseract-ocr/4.00/tessdata

# Копіюємо tessdata з JAR в контейнер
COPY --from=build /target/classes/tessdata /usr/share/tesseract-ocr/4.00/tessdata

# Відкриваємо порт
EXPOSE 8080

# Встановлюємо точку входу
ENTRYPOINT ["java","-jar","highlightserverspring.jar"]
