package com.neuhealth.demo.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrossConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("POST","GET","PUT","OPTIONS","DELETE")
                .allowedOrigins("http://localhost:8087") // 允许的来源
                .maxAge(168000)
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
