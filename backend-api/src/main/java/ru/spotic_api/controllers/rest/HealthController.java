package ru.spotic_api.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HealthController {

    @GetMapping("/health/locale")
    public String getLocale() {
        return "Locale: " + Locale.getDefault() +
                ", Language: " + Locale.getDefault().getLanguage();
    }

}
