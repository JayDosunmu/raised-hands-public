package com.sweteamdragon.raisedhandsserver;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RaisedHandsServerApplication {

    @Value("${com.sweteamdragon.raised-hands.allowed-methods}")
    String allowedMethodsStringList;

    @Value("${com.sweteamdragon.raised-hands.allowed-origins}")
    String allowedOriginsStringList;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOriginsStringList.split(","))
                        .allowedMethods(allowedMethodsStringList.split(","));
            }
        };
    }
    public static void main(String[] args) {
        SpringApplication.run(RaisedHandsServerApplication.class, args);
    }

}
