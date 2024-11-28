package com.xmap_api.controllers;

import com.xmap_api.dto.error.response.DTOErrorResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<DTOErrorResponse> handleException(Exception ex, Locale locale) {
        // TODO залогировать ex, когда будет норм логгер
        String errorMessage = messageSource.getMessage("error.exception.unhandled", null, locale);
        DTOErrorResponse response = DTOErrorResponse.builder().message(errorMessage).build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
