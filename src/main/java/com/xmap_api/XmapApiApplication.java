package com.xmap_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.SpringVersion;

import static com.xmap_api.config.LogbackConfig.Logger.APP_LOG;

@SpringBootApplication
public class XmapApiApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(APP_LOG);

	public static void main(String[] args) {
		SpringApplication.run(XmapApiApplication.class, args);
	}

	// Метод выполняется после того, как приложение полностью запустилось и готово обрабатывать запросы
	@Override
	public void run(String... args) {
		log.info("Application started successfully! : [spring_version = '{}']", SpringVersion.getVersion());
	}

	// Событие старта приложения
	@Bean
	ApplicationListener<ContextRefreshedEvent> onStart() {
		return event -> log.info("Application has been refreshed");
	}

	// Событие остановки приложения
	@Bean
	ApplicationListener<ContextClosedEvent> onStop() {
		return event -> log.info("Application has been closed");
	}
}
