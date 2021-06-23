package com.assignment.recipe.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;


/**
 * Swagger Documentation for Recipe Application
 * @author Srinivas Kaki
 * @version 1.0
 *
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

	/**
	 * To Handle the Authorization token in Swagger documention to Restrict the All services access
	 */
	private ApiKey apiKey() { 
		return new ApiKey("JWT", "Authorization", "header"); 
	}

	private SecurityContext securityContext() { 
		return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
	} 
	private List<SecurityReference> defaultAuth() { 
		var authorizationScope = new AuthorizationScope("global", "accessEverything"); 
		var authorizationScopes = new AuthorizationScope[1]; 
		authorizationScopes[0] = authorizationScope; 
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
	}

	/** 
	 * Register the controllers to swagger and it is configuring the Swagger Docket
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.assignment.recipe.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	/**
	 * Description for Swagger notation for reading the Title and Description about the application
	 */
	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Recipe Applcation",
				"Recipes customation application .",
				"1.0",
				"Add, Update, Delete, Get the Recipes",
				new Contact("Srinivasu Kaki", "www.capgemini.com", "srinivasu.kaki@capgemini.com"),
				"",
				"",
				Collections.emptyList());
	}
}

