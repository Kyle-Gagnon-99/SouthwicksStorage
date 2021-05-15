package com.southwicksstorage.southwicksstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SouthwicksStorageApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SouthwicksStorageApplication.class, args);
		
	}

}
