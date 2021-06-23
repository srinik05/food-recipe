package com.assignment.recipe.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.recipe.entity.Recipe;
import com.assignment.recipe.exceptions.RecipeNotFoundException;
import com.assignment.recipe.model.RecipeBean;
import com.assignment.recipe.service.RecipeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
/**
 * Recipe Application 
 * RecipeController
 * Add your favorite recipe to your DashBoard. 
 * 
 * @author Srinivasu Kaki
 * @version 1.0
 */
@RestController
@RequestMapping("/recipes")  
@Api(tags = {"Favorite Recipe Api"})
@SwaggerDefinition(tags = {
		@Tag(name = "Favorite Recipe Api", description = "Operations pertaining to favorite recipes")
})
public class RecipeController {   

	private static final Logger LOGGER=LoggerFactory.getLogger(RecipeController.class);


	@Autowired
	private RecipeService recipeService;


	/**
	 * Get All Recipes
	 * View a list of my Favorite Recipes from Dashboard
	 * @return RecipeBean as List 
	 */
	@GetMapping("/")
	@ApiOperation(value = "View a list of my Favorite Recipes", response = Recipe.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved list of favourite recipes"),
			@ApiResponse(code = 401, message = "You are not authorized to view the recipes"),
			@ApiResponse(code = 403, message = "Accessing the recipes you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The Recipe you were trying to reach is not found")
	})
	public List<RecipeBean> getAllRecipes() {
		LOGGER.debug("RecipeController||getAllRecipes() - Begin");
		return recipeService.recipesList();
	}


	/**
	 * Get Recipe
	 * Get your Favorite Recipe from Dashboard with RecipeId
	 * @param ID - Enter a valid recipe id to view your recipe
	 * @throws RecipeNotFoundException 
	 * @return RecipeBean- List of all recipes 
	 */
	@GetMapping("{id}") 
	@ApiOperation(value = "Get Your Favorite Recipe By passing RecipeId")
	public RecipeBean getRecipe(@ApiParam(value = "Find a particular Recipe with recipeid", required = true) @PathVariable("id") Long id) throws RecipeNotFoundException {
		LOGGER.debug("RecipeController||getRecipe() - Begin");
		return recipeService.getRecipe(id);
	}


	/**
	 * Create Recipe
	 * Create your Favorite Recipe by adding Recipe name, Instructions and Ingredients.
	 * @param RecipeBean - RecipeBean object is the request body to take the values from frontend
	 * @return RecipeBean - Creating a recipe and storing into the Database 
	 */
	@PostMapping("/")
	@ApiOperation(value = "Create Your Favorite Recipe")
	public RecipeBean createRecipe(@ApiParam(value = "Add your Favourite recipe") @Valid  @RequestBody RecipeBean recipeBean) {
		LOGGER.debug("RecipeController||createRecipe() - Begin");
		return recipeService.createRecipe(recipeBean);
	}


	/**
	 * Update Recipe
	 * Update your Favorite Recipe by changing the desired Instructions and Ingredients.
	 * @param Id -Enter a valid recipe id to update your recipe
	 * @param RecipeBean 
	 * @return RecipeBean - Updating the recipe and storing into the Database 
	 * @throws RecipeNotFoundException
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Update Your Favourite Recipe")
	public RecipeBean updateRecipe(@ApiParam(value = "Update Recipe with recipeId", required = true) @PathVariable("id") Long id,@ApiParam(value = "Update Recipe", required = true) @Valid @RequestBody RecipeBean recipeBean) throws RecipeNotFoundException {
		LOGGER.info("RecipeController||updateRecipe() - Begin");
		return recipeService.updateRecipe(id, recipeBean);
	} 


	/**
	 * Delete Recipe
	 * @param  recipeId- Enter a valid recipe id to delete the recipe
	 * @throws RecipeNotFoundException
	 */ 
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete your Recipe")
	public ResponseEntity<String> deleteRecipe(@ApiParam(value = "Delete Recipe from Dashboard with recipeId", required = true) @PathVariable("id") Long recipeId) throws RecipeNotFoundException {
		LOGGER.info("RecipeController||deleteRecipe() - Begin");
		
		recipeService.deleteRecipe(recipeId);  
		return ResponseEntity.ok().build();
		
	} 

} 
