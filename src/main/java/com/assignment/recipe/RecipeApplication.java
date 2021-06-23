package com.assignment.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Recipe Application
 * Add your favorite recipe to DashBoard.
 * 
 * @author Srinivasu Kaki 
 * @version 1.0
 */

@SpringBootApplication
public class RecipeApplication {


	/** 
	 * Launches the application
	 * @param Application startup arguments
	 * */
	public static void main(String[] args) {
		SpringApplication.run(RecipeApplication.class, args);
	}


	/** 
	 * PasswordEncoder to explicitly provide the PasswordEncoder that our passwords are encoded with.   
	 * Get the singleton NoOpPasswordEncoder.
	 * */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
