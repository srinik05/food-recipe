package com.assignment.recipe.utility;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

/**
 * Recipe Application - JwtFilter Test class
 * 
 * @author Srinivasu Kaki
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = { "jwt.secret=secretkey123", })
public class JWTUtilTest  {

	@Autowired
	JWTUtility jwtUtility;

	/**
	 * Test class for Generate Token
	 */
	@Test
	void testGenerateToken() {
		UserDetails userDetails = new User("admin", "password", new ArrayList<>());
		var token = jwtUtility.generateToken(userDetails);
		assertThat(token).isNotNull();
	}

	/**
	 * Test class for Get the Username from Token
	 */
	@Test
	void testGetUserNameFromToken() {
		UserDetails userDetails = new User("admin", "password", new ArrayList<>());
		var token = jwtUtility.generateToken(userDetails);
		String userName = jwtUtility.getUsernameFromToken(token);
		assertThat(userName).isEqualTo("admin");
	}

	/**
	 * Test class for Validate Token
	 */
	@Test
	void testValidateToken() {
		UserDetails userDetails = new User("admin", "password", new ArrayList<>());
		var token = jwtUtility.generateToken(userDetails);
		assertThat(jwtUtility.validateToken(token, userDetails)).isTrue();
	}
}
