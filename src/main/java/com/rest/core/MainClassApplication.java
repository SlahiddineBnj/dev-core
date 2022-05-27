package com.rest.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaAuditing
//@EnableSwagger2
@SpringBootApplication
public class MainClassApplication {
	public static void main(String[] args) {
		SpringApplication.run(MainClassApplication.class, args);
	}

}
