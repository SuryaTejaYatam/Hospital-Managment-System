package com.HealthCare.HealthCareServiceRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HealthCareServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthCareServiceRegistryApplication.class, args);
	}

}
