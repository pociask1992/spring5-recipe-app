package guru.springframework.converter;

import guru.springframework.dto.RecipeDTO;
import guru.springframework.model.Category;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Notes;
import guru.springframework.model.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class RecipeConverterToDTO implements Converter<Recipe, RecipeDTO> {

    private final NotesConverterToDTO notesConverterToDTO;
    private final CategoryConverterToDTO categoryConverterToDTO;
    private final IngredientConverterToDTO ingredientConverterToDTO;
    public RecipeConverterToDTO(NotesConverterToDTO notesConverterToDTO, CategoryConverterToDTO categoryConverterToDTO, IngredientConverterToDTO ingredientConverterToDTO) {
        this.notesConverterToDTO = notesConverterToDTO;
        this.categoryConverterToDTO = categoryConverterToDTO;
        this.ingredientConverterToDTO = ingredientConverterToDTO;
    }

    @Override
    public RecipeDTO convert(Recipe recipe) {
        RecipeDTO toReturn = new RecipeDTO();
        if(Optional.ofNullable(recipe).isPresent()) {
            toReturn.setId(recipe.getId());
            toReturn.setDescription(recipe.getDescription());
            toReturn.setPrepTime(recipe.getPrepTime());
            toReturn.setCookTime(recipe.getCookTime());
            toReturn.setServings(recipe.getServings());
            toReturn.setSource(recipe.getSource());
            toReturn.setUrl(recipe.getUrl());
            toReturn.setDirections(recipe.getDirections());
            toReturn.setDifficulty(recipe.getDifficulty());
            toReturn.setImages(recipe.getImages());
            toReturn.setBase64Image(recipe.getBase64Image());
            final Optional<Notes> notesOptional = Optional.ofNullable(recipe.getNotes());
            notesOptional.ifPresent(notes -> toReturn.setNotesDTO(notesConverterToDTO.convert(notes)));
            final Set<Category> categories = recipe.getCategories();
            if(Optional.ofNullable(categories).isPresent() && !categories.isEmpty()) {
                toReturn.setCategoriesDTO(categoryConverterToDTO.convert(categories));
            }
            final Set<Ingredient> ingredients = recipe.getIngredients();
            if(Optional.ofNullable(ingredients).isPresent() && !ingredients.isEmpty()) {
                toReturn.setIngredientsDTO(ingredientConverterToDTO.convert(ingredients));
            }
        }
        return toReturn;
    }

    public Set<RecipeDTO> convert(Collection<Recipe> toConvert) {
        Set<RecipeDTO> toReturn = new HashSet<>();
        if(Optional.ofNullable(toConvert).isPresent() &&
                !toConvert.isEmpty()) {
           toConvert.forEach(elemToConvert -> {
               toReturn.add(convert(elemToConvert));
           });
        }
        return toReturn;
    }
}
