package com.example.circuitbreaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CircuitBreakerApplication {

	public static void main(String[] args) {
		//System.setProperty("server.address", "0.0.0.0");
		//System.setProperty("server.port", "5000");
		SpringApplication.run(CircuitBreakerApplication.class, args);
	}

}
