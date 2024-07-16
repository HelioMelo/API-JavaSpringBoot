package com.rocketseat.planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class PlannerApplication {

	public static void main(String[] args) {
		
		 Dotenv dotenv = Dotenv.load();
		 SpringApplication.run(PlannerApplication.class, args);
		
		
	}

}
