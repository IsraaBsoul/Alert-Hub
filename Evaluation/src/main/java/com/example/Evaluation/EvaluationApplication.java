package com.example.Evaluation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EvaluationApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvaluationApplication.class, args);
	}

}
