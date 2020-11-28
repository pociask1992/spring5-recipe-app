package guru.springframework.converter;

import guru.springframework.dto.CategoryDTO;
import guru.springframework.dto.IngredientDTO;
import guru.springframework.dto.NotesDTO;
import guru.springframework.dto.RecipeDTO;
import guru.springframework.model.Category;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Notes;
import guru.springframework.model.Recipe;
import guru.springframework.service.CategoryService;
import guru.springframework.service.IngredientService;
import guru.springframework.service.NotesService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RecipeConverterFromDTO implements Converter<RecipeDTO, Recipe> {

    private final NotesService notesService;
    private final IngredientService ingredientService;
    private final CategoryService categoryService;

    public RecipeConverterFromDTO(NotesService notesService, IngredientService ingredientService, CategoryService categoryService) {
        this.notesService = notesService;
        this.ingredientService = ingredientService;
        this.categoryService = categoryService;
    }

    @Override
    public Recipe convert(RecipeDTO recipeDTO) {
       Recipe toReturn = new Recipe();
       if(Optional.ofNullable(recipeDTO).isPresent()) {
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
           if(Optional.ofNullable(notesDTO).isPresent()) {
               final Optional<Notes> notesOptional = notesService.findById(notesDTO.getId());
               toReturn.setNotes(notesOptional.orElse(null));
           }
           final Set<IngredientDTO> ingredientDTO = recipeDTO.getIngredientsDTO();
           toReturn.setIngredients(prepareIngredientSet(ingredientDTO));

           final Set<CategoryDTO> categoryDTO = recipeDTO.getCategoriesDTO();
           toReturn.setCategories(prepareCategorySet(categoryDTO));
       }
       return toReturn;
    }

    private Set<Category> prepareCategorySet(Set<CategoryDTO> categoryDTO) {
        Set<Category> categorySet = new HashSet<>();
        if(Optional.ofNullable(categoryDTO).isPresent() && !categoryDTO.isEmpty()) {
            final Set<Long> categoryIds = categoryDTO
                     .stream()
                    .map(CategoryDTO::getId)
                    .collect(Collectors.toSet());
            final Iterable<Category> categories = categoryService.findByIds(categoryIds);
            categories.forEach(categorySet::add);
        }
        return categorySet;
    }

    private Set<Ingredient> prepareIngredientSet(Set<IngredientDTO> ingredientDTO) {
        Set<Ingredient> ingredientsSet = new HashSet<>();
        if(Optional.ofNullable(ingredientDTO).isPresent() && !ingredientDTO.isEmpty()) {
            final Set<Long> ingredientIds = convertToIngredientIds(ingredientDTO);
            final Iterable<Ingredient> ingredients = ingredientService.findByIds(ingredientIds);
            ingredients.forEach(ingredientsSet::add);
        }
        return ingredientsSet;
    }

    private Set<Long> convertToIngredientIds(Set<IngredientDTO> ingredientDTO) {
        return ingredientDTO
                           .stream()
                           .map(IngredientDTO::getId)
                           .collect(Collectors.toSet());
    }
}
