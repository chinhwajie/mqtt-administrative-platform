package com.mqtt.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class GlobalSecurityConfig {
    @Autowired
    private MqttJwtAuthenticationFilter mqttAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterAt(mqttAuthenticationFilter, BasicAuthenticationFilter.class);
        http
                .cors(customizer -> {
                    CorsConfigurationSource source = request -> {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(List.of("*"));
                        configuration.setAllowedMethods(List.of("GET", "POST"));
                        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Specify allowed headers
                        return configuration;
                    };
                    customizer.configurationSource(source);
                })
                .authorizeHttpRequests((authorize) -> {
                            authorize.requestMatchers("/auth/login").permitAll();
                            authorize.anyRequest().authenticated();
                        }
                );
        return http.build();
    }
}
