package guru.springframework.converter;

import guru.springframework.dto.IngredientDTO;
import guru.springframework.dto.UnitOfMeasureDTO;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IngredientConverterFromDTO  implements Converter<IngredientDTO, Ingredient> {

    private final RecipeService recipeService;
    private final UnitOfMeasureConverterFromDTO unitOfMeasureConverterFromDTO;

    public IngredientConverterFromDTO(RecipeService recipeService,
                                      UnitOfMeasureConverterFromDTO unitOfMeasureConverterFromDTO) {
        this.recipeService = recipeService;
        this.unitOfMeasureConverterFromDTO = unitOfMeasureConverterFromDTO;
    }

    @Override
    public Ingredient convert(IngredientDTO ingredientDTO) {
        Ingredient toReturn = new Ingredient();
        if(Optional.ofNullable(ingredientDTO).isPresent()) {
            toReturn.setId(ingredientDTO.getId());
            toReturn.setDescription(ingredientDTO.getDescription());
            toReturn.setAmount(ingredientDTO.getAmount());
            final Long recipeId = ingredientDTO.getRecipeId();
            final Recipe returnedRecipe = recipeService.findById(recipeId);
            toReturn.setRecipe(returnedRecipe);
            final UnitOfMeasureDTO unitOfMeasureDTO = ingredientDTO.getUnitOfMeasureDTO();
            toReturn.setUnitOfMeasure(unitOfMeasureConverterFromDTO.convert(unitOfMeasureDTO));
        }
        return toReturn;
    }
}
