package guru.springframework.converter;

import guru.springframework.dto.NotesDTO;
import guru.springframework.model.Notes;
import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotesConverterFromDTO implements Converter<NotesDTO, Notes> {

    private final RecipeService recipeService;

    public NotesConverterFromDTO(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public Notes convert(NotesDTO notesDTO) {
        Notes toReturn = new Notes();
        if(Optional.ofNullable(notesDTO).isPresent()) {
            toReturn.setId(notesDTO.getId());
            toReturn.setRecipeNotes(notesDTO.getRecipeNotes());
            Long recipeId = notesDTO.getRecipeId();
            if(Optional.ofNullable(recipeId).isPresent()) {
                final Recipe foundRecipe = recipeService.findById(recipeId);
                foundRecipe.addNotes(toReturn);
            }
        }
        return toReturn;
    }
}
