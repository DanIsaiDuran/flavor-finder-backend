package com.danduran.flavor_finder.service;

import java.util.List;

import com.danduran.flavor_finder.model.Recipe;

public interface RecipeService {
    Recipe createRecipe(Recipe recipe, Long id);
    Recipe getRecipe(Long id);
    void deleteRecipe(Long id);
    Recipe updateRecipe(Recipe recipe, Long id);
    List<Recipe> getBestFiveRecipes();
    Recipe getRecipeByName(String name);
}