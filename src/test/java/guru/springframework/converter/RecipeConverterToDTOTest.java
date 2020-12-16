package guru.springframework.converter;

import guru.springframework.dto.CategoryDTO;
import guru.springframework.dto.IngredientDTO;
import guru.springframework.dto.NotesDTO;
import guru.springframework.dto.RecipeDTO;
import guru.springframework.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeConverterToDTOTest {

    @Spy
    private Recipe recipeSpy;
    @Mock
    private NotesConverterToDTO notesConverterToDTO;
    @Mock
    private CategoryConverterToDTO categoryConverterToDTO;
    @Mock
    private IngredientConverterToDTO ingredientConverterToDTO;
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
    private final Byte[] IMAGE = new Byte[5];
    private final Long NOTES_ID = 5L;
    private final Set<Long> INGREDIENTS_SET = new HashSet<>(Set.of(100L, 300L, 400L));
    private final Set<Long> CATEGORY_SET = new HashSet<>(Set.of(11L, 22L, 33L));

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

        Notes notesSpy = spy(Notes.class);
        notesSpy.setId(NOTES_ID);
        recipeSpy.setNotes(notesSpy);
        final NotesDTO notesDTOSpy = spy(NotesDTO.class);
        notesDTOSpy.setId(NOTES_ID);

        final Set<Category> categoriesSpy = spy(new HashSet<>());
        CATEGORY_SET.forEach(elem -> {
            final Category categoryToAdd = spy(Category.class);
            categoryToAdd.setId(elem);
            categoriesSpy.add(categoryToAdd);
        });
        recipeSpy.setCategories(categoriesSpy);
        final HashSet<CategoryDTO> categoriesDTOSpy = spy(new HashSet<>());
        CATEGORY_SET.forEach(elem -> {
            final CategoryDTO categoryDTOToAdd = spy(CategoryDTO.class);
            categoryDTOToAdd.setId(elem);
            categoriesDTOSpy.add(categoryDTOToAdd);
        });

        final HashSet<Ingredient> ingredientsSpy = spy(new HashSet<>());
        INGREDIENTS_SET.forEach(elem -> {
            final Ingredient ingredientToAdd = spy(Ingredient.class);
            ingredientToAdd.setId(elem);
            ingredientsSpy.add(ingredientToAdd);
        });
        recipeSpy.setIngredients(ingredientsSpy);

        final HashSet<IngredientDTO> ingredientsDTOSpy = spy(new HashSet<>());
        INGREDIENTS_SET.forEach(elem -> {
            final IngredientDTO ingredientDTOToAdd = spy(IngredientDTO.class);
            ingredientDTOToAdd.setId(elem);
            ingredientsDTOSpy.add(ingredientDTOToAdd);
        });

        //when
        when(notesConverterToDTO.convert(notesSpy)).thenReturn(notesDTOSpy);
        when(categoryConverterToDTO.convert(categoriesSpy)).thenReturn(categoriesDTOSpy);
        when(ingredientConverterToDTO.convert(ingredientsSpy)).thenReturn(ingredientsDTOSpy);
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
        assertEquals(NOTES_ID, returnedRecipeDTO.getNotesDTO().getId());
        assertEquals(INGREDIENTS_SET.size(), returnedRecipeDTO.getIngredientsDTO().size());
        assertEquals(INGREDIENTS_SET, returnedRecipeDTO.getIngredientsDTO().stream().map(IngredientDTO::getId).collect(Collectors.toSet()));
        assertEquals(CATEGORY_SET.size(), returnedRecipeDTO.getCategoriesDTO().size());
        assertEquals(CATEGORY_SET, returnedRecipeDTO.getCategoriesDTO().stream().map(CategoryDTO::getId).collect(Collectors.toSet()));
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
        assertNull(returnedRecipeDTO.getNotesDTO());
        assertTrue(returnedRecipeDTO.getIngredientsDTO().isEmpty());
        assertTrue(returnedRecipeDTO.getCategoriesDTO().isEmpty());
    }

    @Test
    void convertCollection() {
        //given
        final List<Recipe> spyRecipes = spy(new ArrayList<>());
        final Recipe recipe1 = spy(Recipe.class);
        recipe1.setId(1L);
        spyRecipes.add(recipe1);
        final Recipe recipe2 = spy(Recipe.class);
        recipe2.setId(2L);
        spyRecipes.add(recipe2);
        final Recipe recipe3 = spy(Recipe.class);
        recipe3.setId(3L);
        spyRecipes.add(recipe3);
        final Recipe recipe4 = spy(Recipe.class);
        recipe4.setId(4L);
        spyRecipes.add(recipe4);

        //when
        final Set<RecipeDTO> convertedCollection = recipeConverterToDTO.convertCollection(spyRecipes);

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