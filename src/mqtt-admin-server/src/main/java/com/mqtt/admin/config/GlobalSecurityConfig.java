package com.mqtt.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GlobalSecurityConfig {
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    String jwkSetUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(customizer -> {
                    CorsConfigurationSource source = request -> {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
                        configuration.setAllowedMethods(List.of("GET", "POST"));
                        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Specify allowed headers
                        return configuration;
                    };
                    customizer.configurationSource(source);
                })
                .authorizeHttpRequests((authorize) -> {
                            authorize.anyRequest().authenticated();
                        }
                )
                .oauth2ResourceServer(oAuth2ResourceServerConfigurer -> {
                    oAuth2ResourceServerConfigurer.jwt(jwtConfigurer -> {
                        jwtConfigurer.jwtAuthenticationConverter(new JwtAuthenticationConverter());
                    });
                });

        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }
}
