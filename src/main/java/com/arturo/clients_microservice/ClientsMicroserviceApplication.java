package com.arturo.clients_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class ClientsMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientsMicroserviceApplication.class, args);
	}

}
