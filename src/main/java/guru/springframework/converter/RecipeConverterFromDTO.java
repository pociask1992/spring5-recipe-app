package guru.springframework.converter;

import guru.springframework.dto.CategoryDTO;
import guru.springframework.dto.IngredientDTO;
import guru.springframework.dto.NotesDTO;
import guru.springframework.dto.RecipeDTO;
import guru.springframework.model.Category;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Notes;
import guru.springframework.model.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class RecipeConverterFromDTO implements Converter<RecipeDTO, Recipe> {

    private final NotesConverterFromDTO notesConverterFromDTO;
    private final CategoryConverterFromDTO categoryConverterFromDTO;
    private final IngredientConverterFromDTO ingredientConverterFromDTO;

    public RecipeConverterFromDTO(NotesConverterFromDTO notesConverterFromDTO,
                                  CategoryConverterFromDTO categoryConverterFromDTO,
                                  IngredientConverterFromDTO ingredientConverterFromDTO) {
        this.notesConverterFromDTO = notesConverterFromDTO;
        this.categoryConverterFromDTO = categoryConverterFromDTO;
        this.ingredientConverterFromDTO = ingredientConverterFromDTO;
    }

    @Override
    public Recipe convert(RecipeDTO recipeDTO) {
        Recipe toReturn = new Recipe();
        if (Optional.ofNullable(recipeDTO).isPresent()) {
            toReturn.setId(recipeDTO.getId());
            toReturn.setDescription(recipeDTO.getDescription());
            toReturn.setPrepTime(recipeDTO.getPrepTime());
            toReturn.setCookTime(recipeDTO.getCookTime());
            toReturn.setServings(recipeDTO.getServings());
            toReturn.setSource(recipeDTO.getSource());
            toReturn.setUrl(recipeDTO.getUrl());
            toReturn.setDirections(recipeDTO.getDirections());
            toReturn.setDifficulty(recipeDTO.getDifficulty());
            toReturn.setImages(recipeDTO.getImages());
            toReturn.setBase64Image(recipeDTO.getBase64Image());
            final NotesDTO notesDTO = recipeDTO.getNotesDTO();
            if (Optional.ofNullable(notesDTO).isPresent()) {
                final Notes convertedNotes = notesConverterFromDTO.convert(notesDTO);
                toReturn.addNotes(convertedNotes);
            }
            final Set<IngredientDTO> ingredientDTO = recipeDTO.getIngredientsDTO();
            toReturn.addIngredients(prepareIngredientSet(ingredientDTO));

            final Set<CategoryDTO> categoryDTO = recipeDTO.getCategoriesDTO();
            toReturn.addCategories(prepareCategorySet(categoryDTO));
        }
        return toReturn;
    }

    private Set<Category> prepareCategorySet(Set<CategoryDTO> categoryDTO) {
        Set<Category> categorySet = new HashSet<>();
        if (Optional.ofNullable(categoryDTO).isPresent() && !categoryDTO.isEmpty()) {
            categorySet.addAll(categoryConverterFromDTO.convertCollection(categoryDTO));
        }
        return categorySet;
    }

    private Set<Ingredient> prepareIngredientSet(Set<IngredientDTO> ingredientDTO) {
        Set<Ingredient> ingredientsSet = new HashSet<>();
        if (Optional.ofNullable(ingredientDTO).isPresent() && !ingredientDTO.isEmpty()) {
            ingredientsSet.addAll(ingredientConverterFromDTO.convertCollection(ingredientDTO));
        }
        return ingredientsSet;
    }
}
