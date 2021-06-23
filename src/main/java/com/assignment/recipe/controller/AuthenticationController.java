package com.assignment.recipe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.recipe.model.JwtRequest;
import com.assignment.recipe.model.JwtResponse;
import com.assignment.recipe.service.UserService;
import com.assignment.recipe.utility.JWTUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/**
 * Recipe Application 
 * Authentication Controller
 * To authenticate the user by passing username and password
 * 
 * @author Srinivasu Kaki
 * @version 1.0
 */

@RestController
@Api(tags = {"Authentication"})
@SwaggerDefinition(tags = {
		@Tag(name = "Authentication", description = "provide username and password to access the Recipe Application")
})
public class AuthenticationController {

	private static final Logger LOGGER=LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private JWTUtility jwtUtility;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;


	/**
	 * Authentication
	 * Handling this method to authenticate the username and password JWT request
	 * @param jwtRequest username 
	 * @param jwtRequest password
	 * @return JwtReponse jwttoken
	 * @throws BadCredentialsException
	 */
	@PostMapping("/authenticate")
	@ApiOperation(value = "Authenticate by passing Username and Password")
	public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws BadCredentialsException{
		LOGGER.debug("AuthenticationController||authenticate() - Begin");

		try { 
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							jwtRequest.getUsername(),
							jwtRequest.getPassword()
							)
					);
		} catch (BadCredentialsException e) { 
			LOGGER.error("AuthenticationController||authenticate() Invalid Credentials entered- Begin");
			throw new BadCredentialsException("The login credentials which you have entered are invalid. Please provide valid credentials.", e);
		}

		var userDetails
		= userService.loadUserByUsername(jwtRequest.getUsername());

		var token =
				jwtUtility.generateToken(userDetails);

		LOGGER.debug("AuthenticationController||authenticate() Token Generated- End");
		return  new JwtResponse(token);
	}
}
