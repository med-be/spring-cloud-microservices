package com.benoutman.unknown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UnknownServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnknownServiceApplication.class, args);
	}

}
