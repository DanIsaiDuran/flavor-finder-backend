package com.danduran.flavor_finder.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danduran.flavor_finder.exception.RecipeNotFoundException;
import com.danduran.flavor_finder.exception.UserNotFoundException;
import com.danduran.flavor_finder.model.Recipe;
import com.danduran.flavor_finder.service.RecipeService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;



@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/recipe")
public class RecipeController {
    RecipeService recipeService;

    @GetMapping
    public ResponseEntity<Page<Recipe>> findAll (@RequestParam(required = false) Integer difficulty, 
    @RequestParam(required = false) Integer maxPreparationTime,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "5") int size,    
    @RequestParam(defaultValue = "id") String sortBy,
    @RequestParam(required = false) String word
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return new ResponseEntity<>(recipeService.getAll(difficulty, maxPreparationTime, word, pageable), HttpStatus.OK);
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody @Valid Recipe recipe) throws UserNotFoundException {
        return new ResponseEntity<>(recipeService.createRecipe(recipe), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) throws RecipeNotFoundException {
        return new ResponseEntity<>(recipeService.getRecipe(id), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) throws RecipeNotFoundException {
        return new ResponseEntity<>(recipeService.updateRecipe(recipe, id), HttpStatus.OK);
    }

    @GetMapping("/best-five")
    public ResponseEntity<List<Recipe>> getBestFiveRecipes() {
        return new ResponseEntity<>(recipeService.getBestFiveRecipes(), HttpStatus.OK);
    }

    @GetMapping("/find-by-name/{name}")
    public ResponseEntity<Recipe> getRecipeByName(@PathVariable String name) throws RecipeNotFoundException {
        return new ResponseEntity<>(recipeService.getRecipeByName(name), HttpStatus.OK);
    }
    
    @GetMapping("/list/user/{user_id}")
    public ResponseEntity<List<Recipe>> getUserRecipes(@PathVariable Long user_id) {
        return new ResponseEntity<>(recipeService.getUserRecipes(user_id), HttpStatus.OK);
    }
    
}
