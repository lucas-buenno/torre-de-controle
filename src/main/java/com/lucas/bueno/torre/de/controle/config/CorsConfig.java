package com.lucas.bueno.torre.de.controle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/graphql")
                        .allowedOrigins(
                                "http://localhost:8081", // Seu frontend
                                "http://localhost:3000"  // Ou outra porta do frontend
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);

                registry.addMapping("/graphiql") // Bloqueia /graphiql
                        .allowedOrigins()
                        .allowedMethods()
                        .maxAge(0);// Permite cookies/sessões
            }

        };
    }
}