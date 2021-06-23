package com.assignment.recipe.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * 
 * Recipe Application - Recipe Entity class
 * @author Srinivasu Kaki
 * @version 1.0
 */
@Entity
@Table(name = "RECIPE")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Recipe {

	/**
	 * Generating Primary Key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private boolean vegetarian = true;
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date created; 
	private String instructions;
	private int servings;

	/**
	 * Using association OnetoMany with Foregin key id of Ingredients Entity class
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn 
	private List<Ingredient> ingredients;


}
