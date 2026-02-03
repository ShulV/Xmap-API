package ru.spotic_api;

//import static ru.spotic_api.config.LogbackConfig.Logger.APP_LOG;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpoticApiApplication
//		implements CommandLineRunner
{


//	private static final Logger log = LoggerFactory.getLogger(APP_LOG);

	public static void main(String[] args) {
		SpringApplication.run(ru.spotic_api.SpoticApiApplication.class, args);
	}

	// Метод выполняется после того, как приложение полностью запустилось и готово обрабатывать запросы
//	@Override
//	public void run(String... args) {
////		log.info("Application started successfully! : [spring_version = '{}']", SpringVersion.getVersion());
//	}

	// Событие старта приложения
//	@Bean
//	ApplicationListener<ContextRefreshedEvent> onStart() {
////		return event -> log.info("Application has been refreshed");
//	}

	// Событие остановки приложения
//	@Bean
//	ApplicationListener<ContextClosedEvent> onStop() {
////		return event -> log.info("Application has been closed");
//	}
}
