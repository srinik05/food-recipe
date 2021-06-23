package com.assignment.recipe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.assignment.recipe.model.RecipeBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Recipe Application - AuthenticationController Test class
 * 
 * @author Srinivasu Kaki
 */

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	} 
	 
	/**
	 * Authentication success case for passsing valid username and password
	 * @throws Exception
	 */
	@Test
	void authenticationSuccess() throws Exception {
	    String username = "admin"; 
	    String password = "password";

	    String body = "{\"username\":\"" + username + "\", \"password\":\"" + password +"\"}";

	    RequestBuilder requestBuilder = MockMvcRequestBuilders
	    		.post("/authenticate/")
				.accept(MediaType.APPLICATION_JSON)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
 
		String outputInJson = response.getContentAsString(); 
		JSONObject jsonObject = new JSONObject(outputInJson);
		
		String token = jsonObject.getString("jwtToken");
		
		RecipeBean recipeBean = new RecipeBean();
		String receipeInJson = this.mapToJson(recipeBean);

		mockMvc.perform(get("/recipes/")
			      .header("Authorization", "Bearer " + token)
			      .contentType(MediaType.APPLICATION_JSON)
			      .content(receipeInJson)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().is(200));
	}
	
	/**
	 * Failure case for passing wrong password to authenticate the user
	 * @throws Exception 
	 */
	@Test
	void authenticationFailed() throws Exception {
	    String username = "admin";
	    String password = "wrongpassword";

	    String body = "{\"username\":\"" + username + "\", \"password\":\"" + password +"\"}";


		mockMvc.perform(MockMvcRequestBuilders.post("/authenticate/")
				            .content(body))
				            .andExpect(status().is5xxServerError()).andReturn();

	}
	
	/**
	 * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
	 */
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
