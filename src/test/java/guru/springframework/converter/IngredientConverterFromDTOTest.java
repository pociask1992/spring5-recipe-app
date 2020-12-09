package guru.springframework.converter;

import guru.springframework.dto.IngredientDTO;
import guru.springframework.dto.UnitOfMeasureDTO;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Recipe;
import guru.springframework.model.UnitOfMeasure;
import guru.springframework.service.RecipeService;
import guru.springframework.service.UnitOfMeasureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientConverterFromDTOTest {
    @Mock
    RecipeService recipeService;
    @Mock
    UnitOfMeasureService unitOfMeasureService;
    @Mock
    UnitOfMeasureConverterFromDTO unitOfMeasureConverterFromDTO;
    @Spy
    IngredientDTO ingredientDTO;
    @InjectMocks
    IngredientConverterFromDTO ingredientConverterFromDTO;

    private final Long ID = 1L;
    private final String DESCRIPTION = "description";
    private final BigDecimal AMOUNT = new BigDecimal(100);
    private final Long RECIPE_ID = 100L;
    private final Long UNIT_OF_MEASURE_ID = 200L;

    @Test
    void convertWhenRecipeAndUomNotNull() {
        //given
        ingredientDTO.setId(ID);
        ingredientDTO.setDescription(DESCRIPTION);
        ingredientDTO.setAmount(AMOUNT);
        ingredientDTO.setRecipeId(RECIPE_ID);
        final UnitOfMeasureDTO unitOfMeasureDTOSpy = spy(UnitOfMeasureDTO.class);
        unitOfMeasureDTOSpy.setId(UNIT_OF_MEASURE_ID);
        ingredientDTO.setUnitOfMeasureDTO(unitOfMeasureDTOSpy);
        UnitOfMeasure unitOfMeasureSpy = spy(UnitOfMeasure.class);
        unitOfMeasureSpy.setId(UNIT_OF_MEASURE_ID);
        final Recipe spyRecipe = spy(Recipe.class);
        spyRecipe.setId(RECIPE_ID);

        final UnitOfMeasure unitOfMeasure = spy(UnitOfMeasure.class);
        unitOfMeasure.setId(UNIT_OF_MEASURE_ID);

        //when
        when(recipeService.findById(RECIPE_ID)).thenReturn(spyRecipe);
        when(unitOfMeasureService.findById(UNIT_OF_MEASURE_ID)).thenReturn(Optional.of(unitOfMeasureSpy));
        final Ingredient returnedIngredient = ingredientConverterFromDTO.convert(ingredientDTO);

        //then
        assertEquals(ID, returnedIngredient.getId());
        assertEquals(DESCRIPTION, returnedIngredient.getDescription());
        assertEquals(AMOUNT, returnedIngredient.getAmount());
        assertEquals(RECIPE_ID, returnedIngredient.getRecipe().getId());
        assertEquals(UNIT_OF_MEASURE_ID, returnedIngredient.getUnitOfMeasure().getId());
    }

    @Test
    void convertWhenRecipeAndUomNull() {
        //given
        ingredientDTO.setId(ID);
        ingredientDTO.setDescription(DESCRIPTION);
        ingredientDTO.setAmount(AMOUNT);
        ingredientDTO.setRecipeId(null);
        ingredientDTO.setUnitOfMeasureDTO(null);

        //when
        final Ingredient returnedIngredient = ingredientConverterFromDTO.convert(ingredientDTO);

        //then
        assertEquals(ID, returnedIngredient.getId());
        assertEquals(DESCRIPTION, returnedIngredient.getDescription());
        assertEquals(AMOUNT, returnedIngredient.getAmount());
        assertNull(returnedIngredient.getRecipe());
        assertEquals(null, returnedIngredient.getUnitOfMeasure());
    }
}