package guru.springframework.converter;

import guru.springframework.dto.IngredientDTO;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Recipe;
import guru.springframework.model.UnitOfMeasure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IngredientConverterToDTOTest {

    @Spy
    private Ingredient ingredientSpy;
    @InjectMocks
    private IngredientConverterToDTO ingredientConverterToDTO;

    private final Long ID = 1L;
    private final String DESCRIPTION = "description";
    private final BigDecimal AMOUNT = new BigDecimal(100);
    private final Long RECIPE_ID =  100L;
    private final Long UNIT_OF_MEASURE_ID = 200L;

    private AutoCloseable autoCloseable;
    @BeforeEach
    void openMock() {
        autoCloseable = MockitoAnnotations.openMocks(this  );
    }

    @AfterEach
    void releaseMock() throws Exception {
        autoCloseable.close();
    }

    @Test
    void convertWhenRecipeAndUomNotNull() {
        //given
        ingredientSpy.setId(ID);
        ingredientSpy.setDescription(DESCRIPTION);
        ingredientSpy.setAmount(AMOUNT);
        ingredientSpy.setRecipe(new Recipe(RECIPE_ID));
        final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UNIT_OF_MEASURE_ID);
        ingredientSpy.setUnitOfMeasure(unitOfMeasure);

        //when
        final IngredientDTO returnedIngredientDTO = ingredientConverterToDTO.convert(ingredientSpy);

        //then
        assertEquals(ID, returnedIngredientDTO.getId());
        assertEquals(DESCRIPTION, returnedIngredientDTO.getDescription());
        assertEquals(AMOUNT, returnedIngredientDTO.getAmount());
        assertEquals(RECIPE_ID, returnedIngredientDTO.getRecipeId());
        assertEquals(UNIT_OF_MEASURE_ID, returnedIngredientDTO.getUnitOfMeasureDTO());
    }

    @Test
    void convertWhenRecipeAndUomNull() {
        //given
        ingredientSpy.setId(ID);
        ingredientSpy.setDescription(DESCRIPTION);
        ingredientSpy.setAmount(AMOUNT);
        ingredientSpy.setRecipe(null);
        ingredientSpy.setUnitOfMeasure(null);

        //when
        final IngredientDTO returnedIngredientDTO = ingredientConverterToDTO.convert(ingredientSpy);

        //then
        assertEquals(ID, returnedIngredientDTO.getId());
        assertEquals(DESCRIPTION, returnedIngredientDTO.getDescription());
        assertEquals(AMOUNT, returnedIngredientDTO.getAmount());
        assertNull(returnedIngredientDTO.getRecipeId());
        assertNull(returnedIngredientDTO.getUnitOfMeasureDTO());
    }
}