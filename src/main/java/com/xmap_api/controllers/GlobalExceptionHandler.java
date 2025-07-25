package com.xmap_api.controllers;

import com.xmap_api.dto.response.error.ErrorDTO;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {
//    private final Logger log = LoggerFactory.getLogger(APP_LOG);

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDTO> handleException(Exception e, Locale locale) {
//        log.error("Unhandled message", e);
        String errorMessage = messageSource.getMessage("error.exception.unhandled", null, locale);
        ErrorDTO response = ErrorDTO.builder()
                .message(errorMessage)
                .log("message: " + e.getMessage() + "; cause: " + e.getCause())//todo tmp
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
