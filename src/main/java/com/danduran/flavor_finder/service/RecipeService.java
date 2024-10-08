package com.danduran.flavor_finder.service;

import java.util.List;

import com.danduran.flavor_finder.exception.RecipeNotFoundException;
import com.danduran.flavor_finder.exception.UserNotFoundException;
import com.danduran.flavor_finder.model.Recipe;

public interface RecipeService {
    Recipe createRecipe(Recipe recipe, Long id) throws UserNotFoundException;
    Recipe getRecipe(Long id) throws RecipeNotFoundException;
    void deleteRecipe(Long id);
    Recipe updateRecipe(Recipe recipe, Long id) throws RecipeNotFoundException;
    List<Recipe> getBestFiveRecipes();
    Recipe getRecipeByName(String name) throws RecipeNotFoundException;
}