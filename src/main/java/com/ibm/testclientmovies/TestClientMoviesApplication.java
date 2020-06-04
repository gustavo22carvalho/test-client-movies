package com.ibm.testclientmovies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TestClientMoviesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestClientMoviesApplication.class, args);
	}

}
