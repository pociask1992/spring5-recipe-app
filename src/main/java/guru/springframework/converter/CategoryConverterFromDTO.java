package guru.springframework.converter;

import guru.springframework.dto.CategoryDTO;
import guru.springframework.model.Category;
import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class CategoryConverterFromDTO implements Converter<CategoryDTO, Category> {

    private final RecipeService recipeService;

    public CategoryConverterFromDTO(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public Category convert(CategoryDTO categoryDTO) {
        Category toReturn = new Category();
        if(Optional.ofNullable(categoryDTO).isPresent()) {
            toReturn = new Category();
            toReturn.setId(categoryDTO.getId());
            toReturn.setDescription(categoryDTO.getDescription());
            final Set<Long> recipesId = categoryDTO.getRecipesId();
            if(!recipesId.isEmpty()) {
                final Set<Recipe> recipes = recipeService.findByIds(recipesId);
                toReturn.setRecipes(recipes);
            }
        }
        return toReturn;
    }
}
