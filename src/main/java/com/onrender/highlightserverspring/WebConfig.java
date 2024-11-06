package com.onrender.highlightserverspring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Дозволяє доступ до всіх API шляхів
                .allowedOrigins("*")  // Дозволяє доступ з будь-якого джерела
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Дозволяє методи GET, POST, PUT, DELETE
                .allowedHeaders("*")  // Дозволяє всі заголовки
                .allowCredentials(true);  // Дозволяє передачу куків
    }
}
