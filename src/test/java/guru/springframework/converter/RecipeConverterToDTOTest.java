package guru.springframework.converter;

import guru.springframework.dto.RecipeDTO;
import guru.springframework.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

class RecipeConverterToDTOTest {

    @Spy
    Recipe recipeSpy;
    @InjectMocks
    private RecipeConverterToDTO recipeConverterToDTO;

    private final Long ID = 100L;
    private final String DESCRIPTION = "recipe description";
    private final Integer PREP_TIME = 45;
    private final Integer COOK_TIME = 12;
    private final Integer SERVINGS = 5;
    private final String SOURCE = "recipe source";
    private final String URL = "recpe url";
    private final String DIRECTIONS = "recipe directions";
    private final Difficulty DIFFICULTY = Difficulty.EASY;
    private final byte[] IMAGE = new byte[5];
    private final String BASE64_IMAGE = "base64 image";
    private final Long NOTES_ID = 5L;
    private final Set<Long> INGREDIENTS_SET = new HashSet<>(Set.of(100L, 300L, 400L));
    private final Set<Long> CATEGORY_SET = new HashSet<>(Set.of(11L, 22L, 33L));

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
    void convertWhenObjectNotNull() {
        //given
        recipeSpy.setId(ID);
        recipeSpy.setDescription(DESCRIPTION);
        recipeSpy.setPrepTime(PREP_TIME);
        recipeSpy.setCookTime(COOK_TIME);
        recipeSpy.setServings(SERVINGS);
        recipeSpy.setSource(SOURCE);
        recipeSpy.setUrl(URL);
        recipeSpy.setDirections(DIRECTIONS);
        recipeSpy.setDifficulty(DIFFICULTY);
        recipeSpy.setImages(IMAGE);
        recipeSpy.setBase64Image(BASE64_IMAGE);

        Notes notesSpy = spy(Notes.class);
        notesSpy.setId(NOTES_ID);
        recipeSpy.setNotes(notesSpy);

        final Set<Category> categoriesSpy = spy(new HashSet<>());
        CATEGORY_SET.forEach(elem -> {
            final Category categoryToAdd = new Category();
            categoryToAdd.setId(elem);
            categoriesSpy.add(categoryToAdd);
        });
        recipeSpy.setCategories(categoriesSpy);

        final HashSet<Ingredient> ingredientsSpy = spy(new HashSet<>());
        INGREDIENTS_SET.forEach(elem -> {
            final Ingredient ingredientToAdd = new Ingredient();
            ingredientToAdd.setId(elem);
            ingredientsSpy.add(ingredientToAdd);
        });
        recipeSpy.setIngredients(ingredientsSpy);

        //when
        final RecipeDTO returnedRecipeDTO = recipeConverterToDTO.convert(recipeSpy);

        //then
        assertEquals(ID, returnedRecipeDTO.getId());
        assertEquals(DESCRIPTION, returnedRecipeDTO.getDescription());
        assertEquals(PREP_TIME, returnedRecipeDTO.getPrepTime());
        assertEquals(COOK_TIME, returnedRecipeDTO.getCookTime());
        assertEquals(SERVINGS, returnedRecipeDTO.getServings());
        assertEquals(SOURCE, returnedRecipeDTO.getSource());
        assertEquals(URL, returnedRecipeDTO.getUrl());
        assertEquals(DIRECTIONS, returnedRecipeDTO.getDirections());
        assertEquals(DIFFICULTY, returnedRecipeDTO.getDifficulty());
        assertEquals(IMAGE, returnedRecipeDTO.getImages());
        assertEquals(BASE64_IMAGE, returnedRecipeDTO.getBase64Image());
        assertEquals(NOTES_ID, returnedRecipeDTO.getNotesDTO());
        assertEquals(INGREDIENTS_SET.size(), returnedRecipeDTO.getIngredientsDTO().size());
        assertEquals(INGREDIENTS_SET, returnedRecipeDTO.getIngredientsDTO());
        assertEquals(CATEGORY_SET.size(), returnedRecipeDTO.getCategoriesDTO().size());
        assertEquals(CATEGORY_SET, returnedRecipeDTO.getCategoriesDTO());
    }

    @Test
    void convertWhenObjectNull() {
        //given
        recipeSpy.setId(ID);
        recipeSpy.setDescription(DESCRIPTION);
        recipeSpy.setPrepTime(PREP_TIME);
        recipeSpy.setCookTime(COOK_TIME);
        recipeSpy.setServings(SERVINGS);
        recipeSpy.setSource(SOURCE);
        recipeSpy.setUrl(URL);
        recipeSpy.setDirections(DIRECTIONS);
        recipeSpy.setDifficulty(DIFFICULTY);
        recipeSpy.setImages(IMAGE);
        recipeSpy.setBase64Image(BASE64_IMAGE);
        recipeSpy.setNotes(null);

        //when
        final RecipeDTO returnedRecipeDTO = recipeConverterToDTO.convert(recipeSpy);

        //then
        assertEquals(ID, returnedRecipeDTO.getId());
        assertEquals(DESCRIPTION, returnedRecipeDTO.getDescription());
        assertEquals(PREP_TIME, returnedRecipeDTO.getPrepTime());
        assertEquals(COOK_TIME, returnedRecipeDTO.getCookTime());
        assertEquals(SERVINGS, returnedRecipeDTO.getServings());
        assertEquals(SOURCE, returnedRecipeDTO.getSource());
        assertEquals(URL, returnedRecipeDTO.getUrl());
        assertEquals(DIRECTIONS, returnedRecipeDTO.getDirections());
        assertEquals(DIFFICULTY, returnedRecipeDTO.getDifficulty());
        assertEquals(IMAGE, returnedRecipeDTO.getImages());
        assertEquals(BASE64_IMAGE, returnedRecipeDTO.getBase64Image());
        assertNull(returnedRecipeDTO.getNotesDTO());
        assertTrue(returnedRecipeDTO.getIngredientsDTO().isEmpty());
        assertTrue(returnedRecipeDTO.getCategoriesDTO().isEmpty());
    }

    @Test
    void convertCollection() {
        //given
        final List<Recipe> spyRecipes = spy(new ArrayList<>());
        spyRecipes.add(new Recipe(1L));
        spyRecipes.add(new Recipe(2L));
        spyRecipes.add(new Recipe(3L));
        spyRecipes.add(new Recipe(4L));

        //when
        final Set<RecipeDTO> convertedCollection = recipeConverterToDTO.convert(spyRecipes);

        //then
        assertEquals(spyRecipes.size(), convertedCollection.size());
        assertEquals(spyRecipes
                        .stream()
                        .map(Recipe::getId)
                        .collect(Collectors.toSet()),
                convertedCollection
                        .stream()
                        .map(RecipeDTO::getId)
                        .collect(Collectors.toSet()));
    }
}