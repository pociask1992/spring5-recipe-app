package guru.springframework.converter;

import guru.springframework.dto.IngredientDTO;
import guru.springframework.dto.UnitOfMeasureDTO;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import guru.springframework.service.UnitOfMeasureService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class IngredientConverterFromDTO  implements Converter<IngredientDTO, Ingredient> {

    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientConverterFromDTO(RecipeService recipeService,
                                      UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @Override
    public Ingredient convert(IngredientDTO ingredientDTO) {
        Ingredient toReturn = new Ingredient();
        if(Optional.ofNullable(ingredientDTO).isPresent()) {
            toReturn.setId(ingredientDTO.getId());
            toReturn.setDescription(ingredientDTO.getDescription());
            toReturn.setAmount(ingredientDTO.getAmount());
            final Long recipeId = ingredientDTO.getRecipeId();
            if(Optional.ofNullable(recipeId).isPresent()) {
                final Recipe returnedRecipe = recipeService.findById(recipeId);
                if(Optional.ofNullable(returnedRecipe).isPresent()) {
                    returnedRecipe.addIngredient(toReturn);
                }
            }
            final UnitOfMeasureDTO unitOfMeasureDTO = ingredientDTO.getUnitOfMeasureDTO();
            if(Optional.ofNullable(unitOfMeasureDTO).isPresent() && Optional.ofNullable(unitOfMeasureDTO.getId()).isPresent()) {
                toReturn.setUnitOfMeasure(unitOfMeasureService.findById(unitOfMeasureDTO.getId()).orElse(null));
            }
        }
        return toReturn;
    }

    public Set<Ingredient> convertCollection(Collection<IngredientDTO> toConvert) {
        Set<Ingredient> toReturn = new HashSet<>();
        toConvert.forEach(elem -> toReturn.add(convert(elem)));
        return toReturn;
    }
}
