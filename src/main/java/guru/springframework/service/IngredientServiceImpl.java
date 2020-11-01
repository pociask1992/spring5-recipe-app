package guru.springframework.service;

import guru.springframework.model.Ingredient;
import guru.springframework.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient save(Ingredient ingredientToSave) {
        return ingredientRepository.save(ingredientToSave);
    }

    @Override
    public Iterable<Ingredient> save(Set<Ingredient> ingredientsToSave) {
        return ingredientRepository.saveAll(ingredientsToSave);
    }
}
