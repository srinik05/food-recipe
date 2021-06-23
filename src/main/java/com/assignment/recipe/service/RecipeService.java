package com.assignment.recipe.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.assignment.recipe.entity.Recipe;
import com.assignment.recipe.exceptions.RecipeNotFoundException;
import com.assignment.recipe.model.RecipeBean;
import com.assignment.recipe.repository.RecipeRepository;
import com.google.common.reflect.TypeToken;

/**
 * Recipe Application - Recipe Service
 * @author Srinivasu kaki
 * @version 1.0
 */
@Service
public class RecipeService { 

	private static final Logger LOGGER=LoggerFactory.getLogger(RecipeService.class);

	@Autowired 
	private RecipeRepository recipeRepository; 

	/**
	 * Get All Recipes
	 * for getting all recipes from Dashboard
	 * @return RecipeBean - list of all recipesBeans
	 */
	@SuppressWarnings("serial")
	public List<RecipeBean> recipesList() { 
		LOGGER.info("RecipeService|| recipeList()- Begin");
		var modelMapper = new ModelMapper();
		return modelMapper.map(recipeRepository.findAll(), new TypeToken<List<RecipeBean>>() {}.getType());
	}

	/**  
	 * Create Recipe 
	 * for adding recipes to Dashboard
	 * @param RecipeBean 
	 * @return RecipeBean creating recipes and storing into the Database 
	 */
	public RecipeBean createRecipe(RecipeBean recipeBean) {
		LOGGER.info("RecipeService|| createRecipe()- Begin");

		var modelMapper = new ModelMapper();
		var recipe = modelMapper.map(recipeBean, Recipe.class);
		var savedRecipe = recipeRepository.save(recipe);
		return modelMapper.map(savedRecipe, RecipeBean.class);
	}

	/**
	 * FindBy RecipeId
	 * finding the recipeid if not found throwing RecipeNotFoundException
	 * @param RecipeId 
	 * @throws RecipeNotFoundException
	 * @return RecipeBean
	 */
	public RecipeBean getRecipe(Long recipeId)  throws RecipeNotFoundException{
		LOGGER.info("RecipeService|| findByRecipeId()- Begin");
		var modelMapper = new ModelMapper();
		return modelMapper
				.map(recipeRepository
						.findById(recipeId)
						.orElseThrow(() -> new RecipeNotFoundException("Recipe not found for this id :: " + recipeId)), RecipeBean.class);
	}
 

	/**
	 * Delete Recipe
	 * for deleting recipes from Dashboard
	 * @param receipId by passing the recipeid
	 * @throws RecipeNotFoundException 
	 *  
	 */
	public ResponseEntity<String> deleteRecipe(Long recipeId) throws RecipeNotFoundException{
		LOGGER.info("RecipeService|| deleteRecipe()- Begin");
		recipeRepository.findById(recipeId).orElseThrow(()-> new RecipeNotFoundException("Recipe with ID : "+ recipeId + " Not Found!"));
		
		recipeRepository.deleteById(recipeId);
		return ResponseEntity.ok().build();
		
	} 

	/**
	 * Update Recipe
	 * By Passing the recipeid to Update the Recipe from Database
	 * @param recipe id
	 * @param RecipeBean recipe from rest call
	 * @throws RecipeNotFoundException
	 * @return RecipeBean
	 */
	public RecipeBean updateRecipe(Long id, RecipeBean recipeBean) throws RecipeNotFoundException {
		LOGGER.info("RecipeService|| updateRecipe()- Begin");

		Optional<Recipe> currentRecipe = recipeRepository.findById(id);
		var modelMapper = new ModelMapper();
		var recipe = modelMapper.map(recipeBean, Recipe.class);

		if(currentRecipe.isPresent()) {
			var newRecipe = currentRecipe.get();
			newRecipe.setName(recipe.getName());
			newRecipe.setVegetarian(recipe.isVegetarian());
			newRecipe.setInstructions(recipe.getInstructions());
			newRecipe.setServings(recipe.getServings());
			newRecipe.setCreated(recipe.getCreated());
			newRecipe.setIngredients(recipe.getIngredients());

			var savedRecipe = recipeRepository.save(newRecipe);

			return modelMapper.map(savedRecipe, RecipeBean.class);
		}else{
			throw new RecipeNotFoundException("Recipe Not Found....! Please enter valid recipe to update");
		}
	}
}
