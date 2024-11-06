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

# Копіюємо мовні файли tessdata у відповідну директорію Tesseract
# Якщо у вас є папка tessdata у проєкті, копіюємо її
COPY tessdata /usr/share/tesseract-ocr/4.00/tessdata

# Встановлюємо змінну середовища для tessdata
ENV TESSDATA_PREFIX=/usr/share/tesseract-ocr/4.00/

# Копіюємо зібраний jar файл
COPY --from=build /target/highlight-server-spring-0.0.1-SNAPSHOT.jar highlightserverspring.jar

# Відкриваємо порт
EXPOSE 8080

# Встановлюємо точку входу
ENTRYPOINT ["java", "-jar", "highlightserverspring.jar"]
