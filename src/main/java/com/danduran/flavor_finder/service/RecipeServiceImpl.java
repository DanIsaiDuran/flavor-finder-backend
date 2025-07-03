package com.danduran.flavor_finder.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.danduran.flavor_finder.exception.FileProcessingException;
import com.danduran.flavor_finder.exception.RecipeNotFoundException;
import com.danduran.flavor_finder.exception.UserNotFoundException;
import com.danduran.flavor_finder.model.Recipe;
import com.danduran.flavor_finder.model.UserEntity;
import com.danduran.flavor_finder.repository.RecipeRepository;
import com.danduran.flavor_finder.repository.UserRepository;
import com.danduran.flavor_finder.specification.RecipeSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    RecipeRepository recipeRepository;
    UserRepository userRepository;
    FileService fileService;

    @Override
    public Recipe createRecipe(Recipe recipe, MultipartFile image) throws UserNotFoundException, FileProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = authentication.getName();
        UserEntity user = userRepository.findUserByUserName(loggedUsername).orElseThrow(() -> new UserNotFoundException("User not found, recipe couldn't be created"));      
        //TO-DO: Create DTO to catch recipe creation request
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

        if (image != null && !image.isEmpty()) {
            try {
                String fileName = fileService.saveFile(image);
                recipeCreated.setImageFilename(fileName);
                recipeCreated.setImageUrl("/uploads/"+fileName);
            } catch (Exception e) {
                throw new FileProcessingException("Error al guardar la imagen: " + e.getMessage(), e);
            }
            
        }
        return recipeRepository.save(recipeCreated);
    }

    @Override
    public Recipe getRecipe(Long id) throws RecipeNotFoundException {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException("User not found"));
        return recipe;
    }

    @Override
    public void deleteRecipe(Long id) throws RecipeNotFoundException {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(()-> new RecipeNotFoundException("Receta no encontrada"));
        if(recipe.getImageFilename() != null) {
            fileService.deleteFile(recipe.getImageFilename());
        }
        recipeRepository.deleteById(id);
    }

    @Override
    public Recipe updateRecipe(Recipe recipe, Long id, MultipartFile image) throws RecipeNotFoundException, FileProcessingException {
        Recipe recipeOld = recipeRepository.findById(id).orElseThrow(()-> new RecipeNotFoundException("Receta no encontrada"));
        recipeOld.setName(recipe.getName());
        recipeOld.setDescription(recipe.getDescription());
        recipeOld.setSteps(recipe.getSteps());
        recipeOld.setDifficulty(recipe.getDifficulty());
        recipeOld.setPreparationTime(recipe.getPreparationTime());
        recipeOld.setStatus(recipe.getStatus());
        recipeOld.setTools(recipe.getTools());

        if(image != null && !image.isEmpty()){
            try {
                if(recipeOld.getImageFilename() != null){
                    fileService.deleteFile(recipeOld.getImageFilename());
                }

                String fileName = fileService.saveFile(image);
                recipeOld.setImageFilename(fileName);
                recipeOld.setImageUrl("/uploads/"+fileName);
            } catch (Exception e) {
                throw new FileProcessingException("Error al guardar la imagen: " + e.getMessage(), e);
            }
        }
        return recipeRepository.save(recipeOld);
    }

    @Override
    public List<Recipe> getBestFiveRecipes() {
        return (List<Recipe>)recipeRepository.findTop6ByOrderByIdAsc();
    }

    @Override
    public Recipe getRecipeByName(String name) throws RecipeNotFoundException {
        Recipe recipe = recipeRepository.findRecipeByName(name).orElseThrow(()-> new RecipeNotFoundException("Receta no ecnontrada"));
        return recipe;
    }

    @Override
    public List<Recipe> getUserRecipes(Long userId) {
        //TODO: validate if user exists
        return recipeRepository.findByUserId(userId);
    }

    @Override
    public Page<Recipe> getAll(Integer difficulty, Integer maxPreparationTime, String word, Pageable pageable) {
        RecipeSpecification spec = new RecipeSpecification(difficulty, maxPreparationTime, word);
        return recipeRepository.findAll(spec, pageable);
    }
}
