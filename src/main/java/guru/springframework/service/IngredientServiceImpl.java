package guru.springframework.service;

import guru.springframework.model.Ingredient;
import guru.springframework.repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient save(Ingredient ingredientToSave) {
        log.debug("IngredientServiceImpl.save");
        return ingredientRepository.save(ingredientToSave);
    }

    @Override
    public Iterable<Ingredient> save(Set<Ingredient> ingredientsToSave) {
        log.debug("IngredientServiceImpl.save");
        return ingredientRepository.saveAll(ingredientsToSave);
    }
}
