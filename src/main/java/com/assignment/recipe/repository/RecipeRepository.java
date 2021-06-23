package com.assignment.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.recipe.entity.Recipe;

/**
 * Recipe Applcation  - Recipe Repoistory
 * Using Spring Data JPA , JPARepostory for performing operations
 * 
 * @author Srinivasu Kaki
 * @version 1.0
 *
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{

}
