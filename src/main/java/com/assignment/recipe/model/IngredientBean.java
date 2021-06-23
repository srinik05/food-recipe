package com.assignment.recipe.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO class for Ingredients
 * @author Srinivasu Kaki
 * @version 1.0
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IngredientBean { 
	@ApiModelProperty(hidden = true) 
	private Long id;
	private String ingredientName;
	private String quantity;
}
