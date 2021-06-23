package com.assignment.recipe.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Recipe Application - RecipeBean
 * @author Srinivasu Kaki
 * @version 1.0
 *
 */
@Setter 
@Getter
@NoArgsConstructor 
@AllArgsConstructor
@ToString
public class RecipeBean {
	private Long id;
	
	@Size(min=2, max=30 ,message = "please enter valid size of recipe name")
	private String name;
	
	private boolean vegetarian = true;
    
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date created; 
	
	private String instructions;
    @Min(1) @Max(20)
	private int servings;
	
	private List<IngredientBean> ingredients;
	
	
}
