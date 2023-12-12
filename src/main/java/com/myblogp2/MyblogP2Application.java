package com.myblogp2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@SpringBootApplication
public class MyblogP2Application {

	public static void main(String[] args) {

		SpringApplication.run(MyblogP2Application.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){

		return new ModelMapper();
	}

}
