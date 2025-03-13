package com.danduran.flavor_finder.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.danduran.flavor_finder.model.Recipe;


public interface RecipeRepository extends CrudRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe>{
    Optional<Recipe> findRecipeByName(String name);
    List<Recipe> findByUserId(Long userId);
    List<Recipe> findTop6ByOrderByIdAsc();
}
