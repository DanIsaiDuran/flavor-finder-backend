package com.danduran.flavor_finder.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.danduran.flavor_finder.exception.RecipeNotFoundException;
import com.danduran.flavor_finder.exception.UserNotFoundException;
import com.danduran.flavor_finder.model.Recipe;
import com.danduran.flavor_finder.model.UserEntity;
import com.danduran.flavor_finder.repository.RecipeRepository;
import com.danduran.flavor_finder.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    RecipeRepository recipeRepository;
    UserRepository userRepository;

    @Override
    public Recipe createRecipe(Recipe recipe, Long id) throws UserNotFoundException {
        //TO-DO: Create DTO to catch recipe creation request
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found, recipe couldn't be created"));
        Recipe recipeCreated = Recipe.builder()
        .name(recipe.getName())
        .description(recipe.getDescription())
        .steps(recipe.getSteps())
        .ingredients(recipe.getIngredients())
        .difficulty(recipe.getDifficulty())
        .preparationTime(recipe.getPreparationTime())
        .status(recipe.getStatus())
        .tools(recipe.getTools())
        .user(user)
        .build();
        return recipeRepository.save(recipeCreated);
    }

    @Override
    public Recipe getRecipe(Long id) throws RecipeNotFoundException {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException("User not found"));
        return recipe;
    }

    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Recipe updateRecipe(Recipe recipe, Long id) throws RecipeNotFoundException {
        Recipe recipeOld = recipeRepository.findById(id).orElseThrow(()-> new RecipeNotFoundException("Receta no encontrada"));
        recipeOld.setName(recipe.getName());
        recipeOld.setDescription(recipe.getDescription());
        recipeOld.setSteps(recipe.getSteps());
        recipeOld.setDifficulty(recipe.getDifficulty());
        recipeOld.setPreparationTime(recipe.getPreparationTime());
        recipeOld.setStatus(recipe.getStatus());
        recipeOld.setTools(recipe.getTools());
        return recipeRepository.save(recipeOld);
    }

    @Override
    public List<Recipe> getBestFiveRecipes() {
        return (List<Recipe>)recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeByName(String name) throws RecipeNotFoundException {
        Recipe recipe = recipeRepository.findRecipeByName(name).orElseThrow(()-> new RecipeNotFoundException("Receta no ecnontrada"));
        return recipe;
    }
}
