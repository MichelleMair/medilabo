package com.medilabo.diabetes.risk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages="com.medilabo.diabetes.risk.client")
public class MsDiabetesRiskApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsDiabetesRiskApplication.class, args);
	}

}
