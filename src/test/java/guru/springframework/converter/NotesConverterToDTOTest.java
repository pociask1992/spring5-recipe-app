package guru.springframework.converter;

import guru.springframework.dto.NotesDTO;
import guru.springframework.model.Notes;
import guru.springframework.model.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.spy;

class NotesConverterToDTOTest {

    @Spy
    private Notes notes;
    @InjectMocks
    private NotesConverterToDTO notesConverterToDTO;

    private final Long ID = 123L;
    private final String RECIPE_NOTES = "recipe notes";
    private final Long RECIPE_ID = 111L;

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
        notes.setId(ID);
        notes.setRecipeNotes(RECIPE_NOTES);
        final Recipe recipeSpy = spy(Recipe.class);
        recipeSpy.setId(RECIPE_ID);
        notes.setRecipe(recipeSpy);
        //when
        final NotesDTO returnedNotesDTO = notesConverterToDTO.convert(notes);

        //then
        assertEquals(ID, returnedNotesDTO.getId());
        assertEquals(RECIPE_NOTES, returnedNotesDTO.getRecipeNotes());
        assertEquals(RECIPE_ID, returnedNotesDTO.getRecipeId());
    }

    @Test
    void convertWhenRecipeNull() {
        //given
        notes.setId(ID);
        notes.setRecipeNotes(RECIPE_NOTES);

        //when
        final NotesDTO returnedNotesDTO = notesConverterToDTO.convert(notes);

        //then
        assertEquals(ID, returnedNotesDTO.getId());
        assertEquals(RECIPE_NOTES, returnedNotesDTO.getRecipeNotes());
        assertNull(returnedNotesDTO.getRecipeId());
    }
}