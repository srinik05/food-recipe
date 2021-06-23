package com.assignment.recipe.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.assignment.recipe.entity.Recipe;
import com.assignment.recipe.exceptions.RecipeNotFoundException;
import com.assignment.recipe.model.IngredientBean;
import com.assignment.recipe.model.RecipeBean;
import com.assignment.recipe.repository.RecipeRepository;

import static org.mockito.ArgumentMatchers.any;

/**
 * Recipe Applcation - Test the RecipeService
 * @author Srinivasu Kaki
 *
 */
@SpringBootTest
public class RecipeServiceTest {
	
	@InjectMocks
	RecipeService recipeService;
	 
	@Mock
	RecipeRepository recipeRepository;
	
	/**
	 * Preparing the RecipeBean
	 * @return RecipeBean
	 */
	private RecipeBean prepareRecipe() {
		
		RecipeBean recipeBean = new RecipeBean();
		recipeBean.setId(1L);
		recipeBean.setName("Veg Birayni");
		recipeBean.setServings(2);
		recipeBean.setInstructions("Stir in drained rice and water and bring to a boil.");
		recipeBean.setVegetarian(true);
		recipeBean.setCreated(new Date());
		
		IngredientBean ingredientBean1 = new IngredientBean();
		ingredientBean1.setId(1L);
		ingredientBean1.setIngredientName("basmati rice");
		ingredientBean1.setQuantity("1 cup");
		
		IngredientBean ingredientBean2 = new IngredientBean();
		ingredientBean2.setId(2L);
		ingredientBean2.setIngredientName("biryani masala powder");
		ingredientBean2.setQuantity("1 teaspoon");
		
		List<IngredientBean> ingredientBean = new ArrayList<IngredientBean>();
		ingredientBean.add(ingredientBean1);
		ingredientBean.add(ingredientBean2);

		recipeBean.setIngredients(ingredientBean);
		
		return recipeBean;
	}
	 
	/** 
	 * Testcase for returning the crate recipe
	 */	
	@Test 
	void testCreateRecipe() {
		RecipeBean recipeBean = prepareRecipe();
		var modelMapper = new ModelMapper();
		Recipe recipeEntity = modelMapper.map(recipeBean, Recipe.class);
		Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(recipeEntity);
		RecipeBean recipeEntityFromService = recipeService.createRecipe(recipeBean); 
		assertEquals(recipeEntityFromService.getId(),recipeEntity.getId()); 
	}
  
	/**
	 * Test Case for Get All Recipes
	 */
	@Test
	void testGetRecipes() {
		RecipeBean recipeBean = prepareRecipe();

		List<Recipe> entityList = new ArrayList<>();
		Recipe recipeEntity = new ModelMapper().map(recipeBean, Recipe.class);
		entityList.add(recipeEntity);
		
		List<RecipeBean> beanList = new ArrayList<>();
		beanList.add(recipeBean);
		
		Mockito.when(recipeRepository.findAll()).thenReturn(entityList);
		assertThat(recipeService.recipesList().get(0).getName()).isEqualTo(beanList.get(0).getName());
	}
	
	/**
	 * Test case for Deleting the Recipes Success case
	 * @throws RecipeNotFoundException 
	 */
	@Test 
	void testDeleteRecipeForSuccess() throws RecipeNotFoundException {
		Recipe recipe = new Recipe();
		recipe.setId(1l);
		when(recipeRepository.findById(recipe.getId())).thenReturn(Optional.of(recipe));
		recipeService.deleteRecipe(recipe.getId());
		verify(recipeRepository).deleteById(recipe.getId());
	}
	
	/**
	 * Test case for Deleting the Recipes Failure case
	 * @exception RecipeNotFoundException
	 */
//	@Test  
//	void testDeleteRecipeForRecipeNotFound() throws RecipeNotFoundException{
//		Recipe recipe = new Recipe();
//		recipe.setId(1l);
//		when(recipeRepository.findById(recipe.getId())).thenReturn(null);
//		recipeService.deleteRecipe(recipe.getId());
//	}
	  
	/**
	 * Test case for Update Recipe
	 * @throws RecipeNotFoundException
	 */
	@Test
	void testUpdateRecipe() throws RecipeNotFoundException {
		 
		RecipeBean recipeBean = prepareRecipe(); 
		var modelMapper = new ModelMapper();
		Recipe recipeEntity = modelMapper.map(recipeBean, Recipe.class);
		Mockito.when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.of(recipeEntity));
		Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(recipeEntity);
		RecipeBean updatedRecipeFromService = recipeService.updateRecipe(recipeBean.getId(), recipeBean); 
		assertEquals(updatedRecipeFromService.getId(), recipeEntity.getId());
	}
	
	/**
	 * Test Case for Find the Recipe 
	 * @throws RecipeNotFoundException
	 */
	@Test
	void testFindbyRecipeId() throws RecipeNotFoundException {
		RecipeBean recipeBean = prepareRecipe();
		var modelMapper = new ModelMapper();
		Recipe recipeEntity = modelMapper.map(recipeBean, Recipe.class);
		recipeEntity.setId(1l);
		Mockito.when(recipeRepository.findById(recipeEntity.getId())).thenReturn(Optional.of(recipeEntity));
		RecipeBean findByRecipeIdFromService = recipeService.getRecipe(recipeBean.getId());
		assertEquals(findByRecipeIdFromService.getId(), recipeEntity.getId());
	}
}