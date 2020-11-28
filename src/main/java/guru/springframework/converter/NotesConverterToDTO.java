package guru.springframework.converter;

import guru.springframework.dto.NotesDTO;
import guru.springframework.model.Notes;
import guru.springframework.model.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotesConverterToDTO implements Converter<Notes, NotesDTO> {

    @Override
    public NotesDTO convert(Notes notes) {
        NotesDTO notesDTO = new NotesDTO();
        if(Optional.ofNullable(notes).isPresent()) {
            notesDTO.setId(notes.getId());
            notesDTO.setRecipeNotes(notes.getRecipeNotes());
            final Recipe recipe = notes.getRecipe();
            if(Optional.ofNullable(recipe).isPresent()) {
                notesDTO.setRecipeId(recipe.getId());
            }
        }
        return notesDTO;
    }
}
