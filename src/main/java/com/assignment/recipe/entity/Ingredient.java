package com.assignment.recipe.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Recipe Application - Ingredients Entity
 * @author Srinivasu Kaki
 * @version 1.0
 */
@Entity
@Table(name = "INGREDIENT")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ingredient {
	
	/**
	 * 
	 * Creating Primary with Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Long id;
	private String ingredientName;
	private String quantity;
	
}


	
	
	
 
	

