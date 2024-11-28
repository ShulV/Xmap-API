package com.xmap_api.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class BundleMessageConfig {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages", "classpath:errors");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(600); // Время кэширования 10 минут
        messageSource.setFallbackToSystemLocale(false); // Отключить системную локаль
        return messageSource;
    }
}
