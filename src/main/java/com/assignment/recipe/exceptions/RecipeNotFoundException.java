package com.assignment.recipe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Recipe Application - RecipeNotfoundException Class
 * @author Srinivasu Kaki
 * @version 1.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecipeNotFoundException extends Exception{

	 private static final long serialVersionUID = 1L;

	 /**
	  * Constructor for RecipeNotFoundException with proper error message 
	  * @param message
	  */
	 public RecipeNotFoundException(String message){
	     super(message);
	    }

}
