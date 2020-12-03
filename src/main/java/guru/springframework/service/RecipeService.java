package guru.springframework.service;

import guru.springframework.model.Recipe;

import java.util.Collection;
import java.util.Set;

public interface RecipeService {

    Set<Recipe> findAll();
    Recipe findById(Long recipeId);
    Set<Recipe> findByIds(Collection<Long> ids);
    byte[] readImageByName(String imageName);
    Recipe save(Recipe recipeToSave);
    Iterable<Recipe> save(Set<Recipe> recipesToSave);
}
