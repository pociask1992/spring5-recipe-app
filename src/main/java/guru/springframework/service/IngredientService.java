package guru.springframework.service;

import guru.springframework.model.Ingredient;

import java.util.Collection;
import java.util.Set;

public interface IngredientService {
    Iterable<Ingredient> findByIds(Collection<Long> ingredientIds);
    Ingredient save(Ingredient ingredientToSave);
    Iterable<Ingredient> save(Set<Ingredient> ingredientsToSave);
}
