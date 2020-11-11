package guru.springframework.model;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientTest {

    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        ingredient = new Ingredient();
    }

    @Test
    void getIdAndSetId() {
        assertEquals(null, ingredient.getId());

        Long id = 5L;
        ingredient.setId(id);
        assertEquals(id, ingredient.getId());

        id = 0l;
        ingredient.setId(id);
        assertEquals(id, ingredient.getId());

        id = -1l;
        ingredient.setId(id);
        assertEquals(id, ingredient.getId());
    }

    @Test
    void getDescriptionAndSetDescription() {
        assertEquals(null, ingredient.getDescription());

        String description = "description";
        ingredient.setDescription(description);
        assertEquals(description, ingredient.getDescription());

        description = Strings.EMPTY;
        ingredient.setDescription(description);
        assertEquals(description, ingredient.getDescription());

        description = null;
        ingredient.setDescription(description);
        assertEquals(description, ingredient.getDescription());
    }

    @Test
    void getAmountAndSetAmount() {
        assertEquals(null, ingredient.getAmount());

        BigDecimal amount = new BigDecimal(1);
        ingredient.setAmount(amount);
        assertEquals(amount, ingredient.getAmount());

        amount = new BigDecimal(1.11111111111111111);
        ingredient.setAmount(amount);
        assertEquals(amount, ingredient.getAmount());

        amount = new BigDecimal(0);
        ingredient.setAmount(amount);
        assertEquals(amount, ingredient.getAmount());

        amount = new BigDecimal(-1);
        ingredient.setAmount(amount);
        assertEquals(amount, ingredient.getAmount());
    }

    @Test
    void getUnitOfMeasureAndSetUnitOfMeasure() {
        assertEquals(null, ingredient.getUnitOfMeasure());

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        ingredient.setUnitOfMeasure(unitOfMeasure);
        assertEquals(unitOfMeasure, ingredient.getUnitOfMeasure());
    }

    @Test
    void getRecipeAndSetRecipe() {
        assertEquals(null, ingredient.getRecipe());

        Recipe recipe = new Recipe();
        ingredient.setRecipe(recipe);
        assertEquals(recipe, ingredient.getRecipe());
    }
}