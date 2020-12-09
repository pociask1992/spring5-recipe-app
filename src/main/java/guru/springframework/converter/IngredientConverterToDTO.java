package guru.springframework.converter;

import guru.springframework.dto.IngredientDTO;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Recipe;
import guru.springframework.model.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class IngredientConverterToDTO implements Converter<Ingredient, IngredientDTO> {

    private final UnitOfMeasureConverterToDTO unitOfMeasureConverterToDTO;

    public IngredientConverterToDTO(UnitOfMeasureConverterToDTO unitOfMeasureConverterToDTO) {
        this.unitOfMeasureConverterToDTO = unitOfMeasureConverterToDTO;
    }

    @Override
    public IngredientDTO convert(Ingredient ingredient) {
        IngredientDTO toReturn = new IngredientDTO();
        if(Optional.ofNullable(ingredient).isPresent()) {
            toReturn.setId(ingredient.getId());
            toReturn.setDescription(ingredient.getDescription());
            toReturn.setAmount(ingredient.getAmount());
            final Recipe recipe = ingredient.getRecipe();
            if (Optional.ofNullable(recipe).isPresent()) {
                toReturn.setRecipeId(recipe.getId());
            }
            final UnitOfMeasure unitOfMeasure = ingredient.getUnitOfMeasure();
            if (Optional.ofNullable(unitOfMeasure).isPresent()) {
                toReturn.setUnitOfMeasureDTO(unitOfMeasureConverterToDTO.convert(unitOfMeasure));
            }
        }
        return toReturn;
    }

    public Set<IngredientDTO> convert(Collection<Ingredient> toConvert) {
        Set<IngredientDTO> toReturn = new HashSet<>();
        toConvert.forEach(elemToConvert -> {
            toReturn.add(convert(elemToConvert));
        });
        return toReturn;
    }

    public Set<IngredientDTO> convertIterable(Iterable<Ingredient> toConvert) {
        Set<IngredientDTO> toReturn = new LinkedHashSet<>();
        toConvert.spliterator().forEachRemaining(elemToConvert -> {
            toReturn.add(convert(elemToConvert));
        });
        return toReturn;
    }
}
