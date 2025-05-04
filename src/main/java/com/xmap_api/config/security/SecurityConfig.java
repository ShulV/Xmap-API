package com.xmap_api.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity()//pre-post enabled
public class SecurityConfig {

    @Value("${xmap-api.frontend.url}")
    private String frontendUrl;
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    private final KCJwtAuthenticationConverter kcJwtAuthenticationConverter;
    private final KCAuthenticationEntryPoint kcAuthenticationEntryPoint;

    public SecurityConfig(com.xmap_api.config.security.KCJwtAuthenticationConverter kcJwtAuthenticationConverter,
                          KCAuthenticationEntryPoint kcAuthenticationEntryPoint) {
        this.kcJwtAuthenticationConverter = kcJwtAuthenticationConverter;
        this.kcAuthenticationEntryPoint = kcAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Все запросы без авторизации
        http.authorizeHttpRequests(ahr -> ahr
                .requestMatchers("/**").permitAll());
        // csrf выключить
        http.csrf(AbstractHttpConfigurer::disable);
        // cors по умолчанию (настроено в отдельном бине)
        http.cors(Customizer.withDefaults());
        // использовать только защищенное соединение
        http.requiresChannel(cr -> cr
                .anyRequest().requiresSecure());
        // для сервера ресурсов (данного бэкенда)
        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(kcJwtAuthenticationConverter)
                                .jwkSetUri(jwkSetUri)//достаточно того, что это прописано в конфиге, но так более явно
                )
                .authenticationEntryPoint(kcAuthenticationEntryPoint)
        );

        http.sessionManagement(sm -> sm
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of(frontendUrl));
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }
}
