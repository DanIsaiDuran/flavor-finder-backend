package com.danduran.flavor_finder.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danduran.flavor_finder.model.Recipe;
import com.danduran.flavor_finder.service.RecipeService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;



@RestController
@AllArgsConstructor
@RequestMapping("api/v1/recipe")
public class RecipeController {
    RecipeService recipeService;

    @PostMapping("/{user_id}")
    public ResponseEntity<Recipe> createRecipe(@RequestBody @Valid Recipe recipe, @PathVariable Long user_id) {
        return new ResponseEntity<>(recipeService.createRecipe(recipe, user_id), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        return new ResponseEntity<>(recipeService.getRecipe(id), HttpStatus.FOUND);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        return new ResponseEntity<>(recipeService.updateRecipe(recipe, id), HttpStatus.OK);
    }

    @GetMapping("/best-five")
    public ResponseEntity<List<Recipe>> getBestFiveRecipes() {
        return new ResponseEntity<>(recipeService.getBestFiveRecipes(), HttpStatus.FOUND);
    }

    @GetMapping("/find-by-name/{name}")
    public ResponseEntity<Recipe> getRecipeByName(@PathVariable String name) {
        return new ResponseEntity<>(recipeService.getRecipeByName(name), HttpStatus.FOUND);
    }
    
    
}
