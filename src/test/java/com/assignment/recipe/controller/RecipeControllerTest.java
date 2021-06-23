package com.assignment.recipe.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.assignment.recipe.model.IngredientBean;
import com.assignment.recipe.model.RecipeBean;
import com.assignment.recipe.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Recipe Application - RecipeController Test class
 * 
 * @author Srinivasu Kaki
 */
@SpringBootTest 
@AutoConfigureMockMvc
public class RecipeControllerTest {  

	@MockBean
	private RecipeService recipeService;

	@Autowired
	private WebApplicationContext wac; 

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	/**
	 * Preparing Recipebean with Ingredients
	 * @return RecipeBean
	 */
	private RecipeBean prepareRecipeBean() {
		RecipeBean recipeBean = createRecipe("Veg Birayni",2,"Stir in drained rice and water and bring to a boil.",true);

		IngredientBean ingredient1 = createIngredient("basmati rice", "1 cup");
		IngredientBean ingredient2 = createIngredient("biryani masala powder", "biryani masala powder");

		List<IngredientBean> ingredients = new ArrayList<IngredientBean>();
		ingredients.add(ingredient1);
		ingredients.add(ingredient2);

		recipeBean.setIngredients(ingredients);
		return recipeBean;
	}

	/**
	 * Test Case for Create Recipe
	 * @throws Exception
	 */
	@Test 
	void testCreateRecipe() throws Exception {
		RecipeBean recipe = prepareRecipeBean();
		String receipeInJson = this.mapToJson(recipe);

		Mockito 
		.when(recipeService.createRecipe(Mockito.any(RecipeBean.class)))
		.thenReturn(recipe);

		String responseJson = mockMvc
				.perform(MockMvcRequestBuilders
						.post("/recipes/") 
						.accept(MediaType.APPLICATION_JSON)
						.content(receipeInJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse()
				.getContentAsString();

	
		assertThat(responseJson).isEqualTo(receipeInJson);

	}

	/**
	 * Test case for Get All Recipes
	 * @throws Exception
	 */
	@Test
	void testGetAllRecipes() throws Exception {

		List<RecipeBean> recipeList = createRecipeBeans();

		Mockito
		.when(recipeService.recipesList())
		.thenReturn(recipeList);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
						.get("/recipes/")
						.accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String recipeResultFromService = this.mapToJson(recipeList);
		String response = result.getResponse().getContentAsString();

		assertThat(response).isEqualTo(recipeResultFromService);

	}


	/**
	 * Test Case for Get the Recipe with ID
	 * @throws Exception
	 */
	@Test
	void testGetRecipeById() throws Exception {
		RecipeBean recipeBean = prepareRecipeBean();

		Mockito
		.when(recipeService.getRecipe(Mockito.anyLong()))
		.thenReturn(recipeBean);

		MvcResult result = 
				mockMvc
				.perform(MockMvcRequestBuilders
						.get("/recipes/1")
						.accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String recipe = this.mapToJson(recipeBean);
		String response = result.getResponse().getContentAsString();
		assertThat(response).isEqualTo(recipe);

	}

	/**
	 * Test Case for Delte the Recipe
	 * @throws Exception
	 */
	@Test
	void testDeleteRecipe() throws Exception {
		RecipeBean recipeBean = new RecipeBean();

		Mockito
		.when(recipeService.getRecipe(Mockito.anyLong()))
		.thenReturn(recipeBean);

		mockMvc
		.perform(MockMvcRequestBuilders
				.delete("/recipes/1")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}


	/**
	 * Test Case for Update the Recipe
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@Test
	void testUpdateRecipe() throws UnsupportedEncodingException, Exception {
		RecipeBean recipeBean = prepareRecipeBean();

		String receipeBeanInJson = this.mapToJson(recipeBean);

		Mockito
		.when(recipeService.updateRecipe(recipeBean.getId(), recipeBean))
		.thenReturn(recipeBean);


		mockMvc
		.perform(MockMvcRequestBuilders
				.put("/recipes/1") 
				.accept(MediaType.APPLICATION_JSON)
				.content(receipeBeanInJson)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

	}

	/**
	 * Method for Creating the Recipe
	 * @param name
	 * @param servings
	 * @param instructions
	 * @param isVeg
	 * 
	 */
	private RecipeBean createRecipe(String name, int servings, String instructions, boolean isVeg){
		RecipeBean recipeBean = new RecipeBean();
		recipeBean.setId(1L);
		recipeBean.setName(name);
		recipeBean.setServings(servings);
		recipeBean.setInstructions(instructions);
		recipeBean.setVegetarian(isVeg);
		recipeBean.setCreated(new Date());
		return recipeBean;
	}

	/**
	 * Method for creating the list of recipes
	 * 
	 */
	private List<RecipeBean> createRecipeBeans() {
		List<RecipeBean> recipes = new ArrayList<>();
		RecipeBean recipeBean =  createRecipe("Veg Birayni",2,"Stir in drained rice and water and bring to a boil.",true);

		IngredientBean ingredient1 = createIngredient("basmati rice", "1 cup");
		IngredientBean ingredient2 = createIngredient("biryani masala powder", "biryani masala powder");

		List<IngredientBean> ingredients= new ArrayList<IngredientBean>();
		ingredients.add(ingredient1);
		ingredients.add(ingredient2);
		recipeBean.setIngredients(ingredients);
		recipes.add(recipeBean);

		recipeBean = createRecipe("Potato curry",5,"Stir in drained rice and water and bring to a boil.",true);


		ingredient1 = createIngredient("Potatoes", "1 cup");
		ingredient2 = createIngredient("Salt", "1 Spoon");

		ingredients= new ArrayList<IngredientBean>();
		ingredients.add(ingredient1);
		ingredients.add(ingredient2);


		recipeBean.setIngredients(ingredients);
		recipes.add(recipeBean);

		return recipes;

	}

	/**
	 * Method for Creating the Ingredients
	 * @param name
	 * @param quantity
	 * 
	 */
	private IngredientBean createIngredient(String name, String quantity){
		IngredientBean ingredient = new IngredientBean();
		ingredient.setId(1L); 
		ingredient.setIngredientName(name);
		ingredient.setQuantity(quantity);
		return ingredient;
	}


	/**
	 * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
	 */
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}


}