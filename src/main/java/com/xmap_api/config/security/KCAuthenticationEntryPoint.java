package com.xmap_api.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xmap_api.controllers.GlobalExceptionHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KCAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GlobalExceptionHandler globalExceptionHandler;

    public KCAuthenticationEntryPoint(GlobalExceptionHandler globalExceptionHandler) {
        this.globalExceptionHandler = globalExceptionHandler;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        // перенаправляем в обработчик
        globalExceptionHandler.handleAccessDeniedException(e, request.getLocale(), request); // Передаем исключение в общий обработчик
    }
}
