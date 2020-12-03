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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeConverterFromDTOTest {
    @Mock
    private NotesConverterFromDTO notesConverterFromDTO;
    @Mock
    private CategoryConverterFromDTO categoryConverterFromDTO;
    @Mock
    private IngredientConverterFromDTO ingredientConverterFromDTO;
    @Spy
    private RecipeDTO recipeDTOSpy;
    @InjectMocks
    private RecipeConverterFromDTO recipeConverterFromDTO;

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

    @Test
    void convertWhenObjectsNotNull() {
        //given
        recipeDTOSpy.setId(ID);
        recipeDTOSpy.setDescription(DESCRIPTION);
        recipeDTOSpy.setPrepTime(PREP_TIME);
        recipeDTOSpy.setCookTime(COOK_TIME);
        recipeDTOSpy.setServings(SERVINGS);
        recipeDTOSpy.setSource(SOURCE);
        recipeDTOSpy.setUrl(URL);
        recipeDTOSpy.setDirections(DIRECTIONS);
        recipeDTOSpy.setDifficulty(DIFFICULTY);
        recipeDTOSpy.setImages(IMAGE);
        recipeDTOSpy.setBase64Image(BASE64_IMAGE);
        final NotesDTO notesDTOSpy = spy(NotesDTO.class);
        notesDTOSpy.setId(NOTES_ID);
        recipeDTOSpy.setNotesDTO(notesDTOSpy);

        Set<CategoryDTO> categoriesDTO = new HashSet<>();
        final Set<CategoryDTO> spyCategoriesDTO = spy(categoriesDTO);
        CATEGORY_SET.forEach(elem -> {
            final CategoryDTO categoryDTOSpy = spy(CategoryDTO.class);
            categoryDTOSpy.setId(elem);
            spyCategoriesDTO.add(categoryDTOSpy);
        });
        recipeDTOSpy.setCategoriesDTO(spyCategoriesDTO);

        Set<IngredientDTO> ingredientDTOS = new HashSet<>();
        final Set<IngredientDTO> spyIngredientsDTO = spy(ingredientDTOS);
        INGREDIENTS_SET.forEach(elem -> {
            final IngredientDTO ingredientDTOSpy = spy(IngredientDTO.class);
            ingredientDTOSpy.setId(elem);
            spyIngredientsDTO.add(ingredientDTOSpy);
        });
        recipeDTOSpy.setIngredientsDTO(ingredientDTOS);

        final Notes notesSpy = spy(Notes.class);
        notesSpy.setId(NOTES_ID);

        Set<Ingredient> ingredients = new HashSet<>();
        final Set<Ingredient> ingredientsSpy = spy(ingredients);
        INGREDIENTS_SET.forEach(elem -> {
            Ingredient ingredientToAdd = new Ingredient();
            ingredientToAdd.setId(elem);
            ingredientsSpy.add(ingredientToAdd);
        });

        Set<Category> categories = new HashSet<>();
        final Set<Category> categoriesSpy = spy(categories);
        CATEGORY_SET.forEach(elem -> {
            Category categoryToAdd = new Category();
            categoryToAdd.setId(elem);
            categoriesSpy.add(categoryToAdd);
        });

        //when
        when(ingredientConverterFromDTO.convertCollection(anyCollection())).thenReturn(ingredients);
        when(categoryConverterFromDTO.convertCollection(anyCollection())).thenReturn(categories);
        when(notesConverterFromDTO.convert(notesDTOSpy)).thenReturn(notesSpy);
        final Recipe returnedRecipe = recipeConverterFromDTO.convert(recipeDTOSpy);

        //then
        assertEquals(ID, returnedRecipe.getId());
        assertEquals(DESCRIPTION, returnedRecipe.getDescription());
        assertEquals(PREP_TIME, returnedRecipe.getPrepTime());
        assertEquals(COOK_TIME, returnedRecipe.getCookTime());
        assertEquals(SERVINGS, returnedRecipe.getServings());
        assertEquals(SOURCE, returnedRecipe.getSource());
        assertEquals(URL, returnedRecipe.getUrl());
        assertEquals(DIRECTIONS, returnedRecipe.getDirections());
        assertEquals(DIFFICULTY, returnedRecipe.getDifficulty());
        assertEquals(IMAGE, returnedRecipe.getImages());
        assertEquals(BASE64_IMAGE, returnedRecipe.getBase64Image());
        assertEquals(NOTES_ID, returnedRecipe.getNotes().getId());
        assertEquals(INGREDIENTS_SET.size(), returnedRecipe.getIngredients().size());
        assertEquals(INGREDIENTS_SET, returnedRecipe.getIngredients().stream().map(Ingredient::getId).collect(Collectors.toSet()));
        assertEquals(CATEGORY_SET.size(), returnedRecipe.getCategories().size());
        assertEquals(CATEGORY_SET, returnedRecipe.getCategories().stream().map(Category::getId).collect(Collectors.toSet()));
    }

    @Test
    void convertWhenObjectsNull() {
        //given
        recipeDTOSpy.setId(ID);
        recipeDTOSpy.setDescription(DESCRIPTION);
        recipeDTOSpy.setPrepTime(PREP_TIME);
        recipeDTOSpy.setCookTime(COOK_TIME);
        recipeDTOSpy.setServings(SERVINGS);
        recipeDTOSpy.setSource(SOURCE);
        recipeDTOSpy.setUrl(URL);
        recipeDTOSpy.setDirections(DIRECTIONS);
        recipeDTOSpy.setDifficulty(DIFFICULTY);
        recipeDTOSpy.setImages(IMAGE);
        recipeDTOSpy.setBase64Image(BASE64_IMAGE);

        //when
        final Recipe returnedRecipe = recipeConverterFromDTO.convert(recipeDTOSpy);

        //then
        assertEquals(ID, returnedRecipe.getId());
        assertEquals(DESCRIPTION, returnedRecipe.getDescription());
        assertEquals(PREP_TIME, returnedRecipe.getPrepTime());
        assertEquals(COOK_TIME, returnedRecipe.getCookTime());
        assertEquals(SERVINGS, returnedRecipe.getServings());
        assertEquals(SOURCE, returnedRecipe.getSource());
        assertEquals(URL, returnedRecipe.getUrl());
        assertEquals(DIRECTIONS, returnedRecipe.getDirections());
        assertEquals(DIFFICULTY, returnedRecipe.getDifficulty());
        assertEquals(IMAGE, returnedRecipe.getImages());
        assertEquals(BASE64_IMAGE, returnedRecipe.getBase64Image());
        assertNull(returnedRecipe.getNotes());
        assertTrue(returnedRecipe.getIngredients().isEmpty());
        assertTrue(returnedRecipe.getCategories().isEmpty());
    }
}