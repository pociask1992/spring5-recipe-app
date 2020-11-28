package guru.springframework.converter;

import guru.springframework.dto.NotesDTO;
import guru.springframework.model.Notes;
import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class NotesConverterFromDTOTest {

    @Mock
    private RecipeService recipeService;
    @Spy
    private NotesDTO notesDTO;
    @InjectMocks
    private NotesConverterFromDTO notesConverterFromDTO;

    private final Long ID = 5L;
    private final Long RECIPE_ID = 700L;
    private final String RECIPE_NOTES = "recipe notes";

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
    void convertWhenRecipeNotNull() {
        //given
        notesDTO.setId(ID);
        notesDTO.setRecipeId(RECIPE_ID);
        notesDTO.setRecipeNotes(RECIPE_NOTES);
        final Recipe recipeSpy = spy(Recipe.class);
        recipeSpy.setId(RECIPE_ID);

        //when
        when(recipeService.findById(anyLong())).thenReturn(recipeSpy);
        final Notes returnedNotes = notesConverterFromDTO.convert(notesDTO);

        //then
        assertEquals(ID, returnedNotes.getId());
        assertEquals(RECIPE_ID, returnedNotes.getRecipe().getId());
        assertEquals(RECIPE_NOTES, returnedNotes.getRecipeNotes());
    }

    @Test
    void convertWhenRecipeNull() {
        //given
        notesDTO.setId(ID);
        notesDTO.setRecipeId(null);
        notesDTO.setRecipeNotes(RECIPE_NOTES);

        //when
        when(recipeService.findById(anyLong())).thenReturn(null);
        final Notes returnedNotes = notesConverterFromDTO.convert(notesDTO);

        //then
        assertEquals(ID, returnedNotes.getId());
        assertNull(returnedNotes.getRecipe());
        assertEquals(RECIPE_NOTES, returnedNotes.getRecipeNotes());
    }
}