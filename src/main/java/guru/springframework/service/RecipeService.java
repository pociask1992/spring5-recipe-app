package guru.springframework.service;

import guru.springframework.model.Recipe;

import java.util.Collection;
import java.util.Set;

public interface RecipeService {

    Set<Recipe> findAll();
    Set<Recipe> findAllOrderByDescriptionDescAndIdAsc();
    Recipe findById(Long recipeId);
    Set<Recipe> findByIds(Collection<Long> ids);
    Recipe save(Recipe recipeToSave);
    Iterable<Recipe> save(Set<Recipe> recipesToSave);
    void deleteById(Long id);
    void deleteAll();
}
