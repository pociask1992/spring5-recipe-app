package guru.springframework.service;

import guru.springframework.dto.IngredientDTO;
import guru.springframework.model.Ingredient;

import java.util.Collection;
import java.util.Set;

public interface IngredientService {
    Set<IngredientDTO> findByRecipeId(Long recipeId);
    IngredientDTO findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    Set<IngredientDTO> findByIds(Collection<Long> ingredientIds);
    IngredientDTO save(Ingredient ingredientToSave);
    Iterable<IngredientDTO> save(Set<Ingredient> ingredientsToSave);
    IngredientDTO updateIngredientByRecipeIdAndIngredientId(IngredientDTO ingredientToSave, Long recipeId);
    void deleteIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
