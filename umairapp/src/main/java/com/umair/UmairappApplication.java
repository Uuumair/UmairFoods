package com.umair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
 @ComponentScan(basePackages = "com.umair")
public class UmairappApplication {      

	public static void main(String[] args) {
		SpringApplication.run(UmairappApplication.class, args);
	}

}
