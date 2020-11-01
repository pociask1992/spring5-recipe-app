package guru.springframework.service;

import guru.springframework.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> findAll();
    byte[] readImageByName(String imageName);
    void save(Recipe recipeToSave);
    Iterable<Recipe> save(Set<Recipe> recipesToSave);
}
