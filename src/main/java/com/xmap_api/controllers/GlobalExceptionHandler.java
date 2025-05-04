package com.xmap_api.controllers;

import com.xmap_api.dto.response.error.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.xmap_api.config.LogbackConfig.Logger.APP_LOG;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(APP_LOG);

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorDTO> handleAccessDeniedException(
            Exception e, Locale locale, HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("test", 1);
        params.put("type", e.getClass().getName());
        params.put("class", e.getClass());
        params.put("message", e.getMessage());
        params.put("path", request.getServletPath());
        params.put("timestamp", LocalDateTime.now());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorDTO response = ErrorDTO.builder()
                .message(messageSource.getMessage("error.forbidden", null, locale))
                .params(params)
                .build();

        return new ResponseEntity<>(response, headers, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDTO> handleException(Exception e, Locale locale) {
        log.error("Unhandled message", e);
        String errorMessage = messageSource.getMessage("error.exception.unhandled", null, locale);
        ErrorDTO response = ErrorDTO.builder().message(errorMessage).build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
