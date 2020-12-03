package guru.springframework.converter;

import guru.springframework.dto.NotesDTO;
import guru.springframework.model.Notes;
import guru.springframework.model.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class NotesConverterToDTOTest {

    @Spy
    private Notes notes;
    @InjectMocks
    private NotesConverterToDTO notesConverterToDTO;

    private final Long ID = 123L;
    private final String RECIPE_NOTES = "recipe notes";
    private final Long RECIPE_ID = 111L;

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