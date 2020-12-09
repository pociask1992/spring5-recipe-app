package guru.springframework.service;

import guru.springframework.converter.IngredientConverterFromDTO;
import guru.springframework.converter.IngredientConverterToDTO;
import guru.springframework.dto.IngredientDTO;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Recipe;
import guru.springframework.repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeService recipeService;
    private final IngredientConverterToDTO ingredientConverterToDTO;
    private final IngredientConverterFromDTO ingredientConverterFromDTO;

    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 RecipeService recipeService,
                                 IngredientConverterToDTO ingredientConverterToDTO,
                                 IngredientConverterFromDTO ingredientConverterFromDTO) {
        this.ingredientRepository = ingredientRepository;
        this.recipeService = recipeService;
        this.ingredientConverterToDTO = ingredientConverterToDTO;
        this.ingredientConverterFromDTO = ingredientConverterFromDTO;
    }

    @Override
    public Set<IngredientDTO> findByRecipeId(Long recipeId) {
        final Iterable<Ingredient> foundIngredients = findByRecipe_IdOrderByIdAsc(recipeId);
        return ingredientConverterToDTO.convertIterable(foundIngredients);
    }

    @Override
    public IngredientDTO findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Set<Ingredient> foundIngredientSet = convertToSet(recipeId);
        final Ingredient foundIngredient = foundIngredientSet
                .stream()
                .filter(ingredient -> ingredientId.equals(ingredient.getId()))
                .findFirst()
                .orElse(null);
        return ingredientConverterToDTO.convert(foundIngredient);
    }

    private Set<Ingredient> convertToSet(Long recipeId) {
        Set<Ingredient> toReturn = new LinkedHashSet<>();
        final Iterable<Ingredient> foundIngredients = findByRecipe_IdOrderByIdAsc(recipeId);
        foundIngredients.spliterator().forEachRemaining(toReturn::add);
        return toReturn;
    }

    @Override
    public Set<IngredientDTO> findByIds(Collection<Long> ingredientIds) {
        log.debug("IngredientServiceImpl.findByIds");
        final Iterable<Ingredient> foundIngredients = ingredientRepository.findAllById(ingredientIds);
        return ingredientConverterToDTO.convertIterable(foundIngredients);
    }

    @Override
    public IngredientDTO save(Ingredient ingredientToSave) {
        log.debug("IngredientServiceImpl.save");
        return ingredientConverterToDTO.convert(ingredientRepository.save(ingredientToSave));
    }

    @Override
    public Iterable<IngredientDTO> save(Set<Ingredient> ingredientsToSave) {
        log.debug("IngredientServiceImpl.save");
        return ingredientConverterToDTO.convertIterable(ingredientRepository.saveAll(ingredientsToSave));
    }


    @Override
    public IngredientDTO updateIngredientByRecipeIdAndIngredientId(IngredientDTO ingredientToSaveDTO, Long recipeId) {
        IngredientDTO savedIngredient = new IngredientDTO();
        final Optional<Recipe> recipeOptional = Optional.ofNullable(recipeService.findById(recipeId));
        if(recipeOptional.isPresent()) {
            final Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientToSaveDTO.getId());
            if(ingredientOptional.isPresent()) {
                final Recipe recipe = recipeOptional.get();
                final Ingredient ingredient = ingredientOptional.get();
                recipe.removeIngredient(ingredient);
                final Ingredient ingredientToSave = ingredientConverterFromDTO.convert(ingredientToSaveDTO);
                recipe.addIngredient(ingredientToSave);
                savedIngredient = ingredientConverterToDTO.convert(ingredientRepository.save(ingredientToSave));
            } else {
                log.error(String.format("IngredientServiceImpl.updateIngredientByRecipeIdAndIngredientId " +
                        "Ingredient was not found. recipeId:%d ingredientId:%d", recipeId, ingredientToSaveDTO.getId()));
            }
        } else {
            log.error(String.format("IngredientServiceImpl.updateIngredientByRecipeIdAndIngredientId " +
                    "Recipe was not found. recipeId:%d ingredientId:%d", recipeId, ingredientToSaveDTO.getId()));
        }
        return savedIngredient;
    }

    @Override
    public void deleteIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        final Optional<Recipe> recipeOptional = Optional.ofNullable(recipeService.findById(recipeId));
        if(recipeOptional.isPresent()) {
            final Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);
            if(ingredientOptional.isPresent()) {
                final Recipe recipe = recipeOptional.get();
                final Ingredient ingredient = ingredientOptional.get();
                recipe.removeIngredient(ingredient);
                ingredientRepository.delete(ingredient);
            } else {
                log.error(String.format("IngredientServiceImpl.deleteIngredientByRecipeIdAndIngredientId Ingredient was not found. recipeId:%d ingredientId:%d", recipeId, ingredientId));
            }
        } else {
            log.error(String.format("IngredientServiceImpl.deleteIngredientByRecipeIdAndIngredientId Recipe was not found. recipeId:%d ingredientId:%d", recipeId, ingredientId));
        }
    }

    private Iterable<Ingredient> findByRecipe_IdOrderByIdAsc(Long recipeId) {
        return ingredientRepository.findByRecipe_Id(recipeId, Sort.by(Sort.Direction.ASC, "id"));
    }
}
