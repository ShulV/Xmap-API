package com.xmap_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XmapApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(XmapApiApplication.class, args);
	}

	//TODO обработать XmapApiException, научить его принимать сообщения
}
