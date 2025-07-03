package com.danduran.flavor_finder.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.danduran.flavor_finder.exception.RecipeNotFoundException;
import com.danduran.flavor_finder.exception.UserNotFoundException;
import com.danduran.flavor_finder.model.Recipe;

public interface RecipeService {
    Recipe createRecipe(Recipe recipe, MultipartFile image) throws UserNotFoundException;
    Recipe getRecipe(Long id) throws RecipeNotFoundException;
    void deleteRecipe(Long id) throws RecipeNotFoundException;
    Recipe updateRecipe(Recipe recipe, Long id, MultipartFile image) throws RecipeNotFoundException;
    List<Recipe> getBestFiveRecipes();
    Recipe getRecipeByName(String name) throws RecipeNotFoundException;
    List<Recipe> getUserRecipes(Long userId);
    Page<Recipe> getAll(Integer difficulty, Integer maxPreparationTime, String word, Pageable pageable);
}