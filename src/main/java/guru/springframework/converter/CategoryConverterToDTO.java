package guru.springframework.converter;

import guru.springframework.dto.CategoryDTO;
import guru.springframework.model.Category;
import guru.springframework.model.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryConverterToDTO implements Converter<Category, CategoryDTO> {

    @Override
    public CategoryDTO convert(Category category) {
        CategoryDTO toReturn = new CategoryDTO();
        if(Optional.ofNullable(category).isPresent()) {
            toReturn.setId(category.getId());
            toReturn.setDescription(category.getDescription());
            final Set<Recipe> recipes = category.getRecipes();
            if(Optional.ofNullable(recipes).isPresent() && !recipes.isEmpty()) {
                final Set<Long> recipesToAdd = recipes
                        .stream()
                        .map(Recipe::getId)
                        .collect(Collectors.toSet());
                toReturn.setRecipesId(recipesToAdd);
            }
        }
        return toReturn;
    }

    public Set<CategoryDTO> convert(Collection<Category> toConvert) {
        Set<CategoryDTO> toReturn = new HashSet<>();
        toConvert.forEach(elemToConvert -> {
            toReturn.add(convert(elemToConvert));
        });
        return toReturn;
    }
}
