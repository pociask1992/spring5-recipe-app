package guru.springframework.service;

import guru.springframework.model.Ingredient;

import java.util.Set;

public interface IngredientService {

    Ingredient save(Ingredient ingredientToSave);
    Iterable<Ingredient> save(Set<Ingredient> ingredientsToSave);
}
