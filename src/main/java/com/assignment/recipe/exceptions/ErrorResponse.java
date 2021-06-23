package com.assignment.recipe.exceptions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Recipe Applcation - Handling Exceptions with Error Details for response
 * @author Srinivasu Kaki
 * @version 1.0
 *
 */ 
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorResponse {

	private int status;
	private Date timestamp;
	private String message;
	private String details;
}
