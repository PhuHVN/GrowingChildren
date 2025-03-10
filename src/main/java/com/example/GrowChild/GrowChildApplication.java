package com.example.GrowChild;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.GrowChild")
public class GrowChildApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrowChildApplication.class, args);
		System.out.println("Running");
	}
}
