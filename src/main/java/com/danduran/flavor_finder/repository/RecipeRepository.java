package com.danduran.flavor_finder.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.danduran.flavor_finder.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long>{
    Optional<Recipe> findRecipeByName(String name); 
}
