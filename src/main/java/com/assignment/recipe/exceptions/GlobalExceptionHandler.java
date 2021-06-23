package com.assignment.recipe.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
/**
 * Recipe Application - Gobal Exception Handler
 * @author Srinivasu kaki
 * @version 1.0
 */
@ControllerAdvice  
public class GlobalExceptionHandler {
	 
	/**
	 * Entered recipe is not found then returing RecipeNotFoundExpception
	 * @param exception message 
	 * @param request
	 * @return ErrorResonse as Message , timestamp and error
	 */
	@ExceptionHandler(RecipeNotFoundException.class) 
	public ResponseEntity<ErrorResponse> recipeNotFoundException(RecipeNotFoundException ex, WebRequest request) {
		var response = new ErrorResponse();
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setMessage(ex.getMessage());
		response.setTimestamp(new Date());
		response.setDetails(request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Returning the Internal Server error for handing global exceptions
	 * @param exception 
	 * @param request
	 * @return ErrorResonse as Internal server error
	 */
	@ExceptionHandler(Exception.class) 
	public ResponseEntity<ErrorResponse> globleExcpetionHandler(Exception ex, WebRequest request) {
		var response = new ErrorResponse();
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setMessage(ex.getLocalizedMessage());
		response.setTimestamp(new Date());
		response.setDetails(request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	/**
	 * BadCredentialsException while username or password entered wrongly
	 * @param exception 
	 * @param request
	 * @return ErrorResonse as 401 unauthorized exception
	 */
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> badCredentialsException(BadCredentialsException bce, WebRequest request) {
		var response = new ErrorResponse();
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setMessage(bce.getMessage());
		response.setTimestamp(new Date());
		response.setDetails(request.getDescription(false));
		
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	
}