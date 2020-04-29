package com.sweteamdragon.raisedhandsserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Value("${com.sweteamdragon.raised-hands.allowed-origins}")
    String allowedOriginsListString;

    @Value("${com.sweteamdragon.raised-hands.allowed-methods}")
    String allowedMethodsListString;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOriginsListString.split(","))
                        .allowedMethods(allowedMethodsListString.split(","));
            }
        };
    }
}
