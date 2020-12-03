package guru.springframework.converter;

import guru.springframework.dto.CategoryDTO;
import guru.springframework.model.Category;
import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryConverterFromDTOTest {

    @Mock
    RecipeService recipeService;
    @Spy
    CategoryDTO categoryDTOMock;
    @InjectMocks
    CategoryConverterFromDTO categoryConverterFromDT;

    private final Long ID = 1L;
    private final String DESCRIPTION = "description";
    private final Set<Long> IDS = new HashSet<>(Set.of(1L, 2L, 3L));

    @Test
    void convertWhenNotNull() {
        //given
        categoryDTOMock.setId(ID);
        categoryDTOMock.setDescription(DESCRIPTION);
        categoryDTOMock.setRecipesId(IDS);
        List<Long> idsToAdd = new ArrayList<>(IDS);

        Set<Recipe> recipes = new HashSet<>();
        final Set<Recipe> spyRecipes = spy(recipes);
        final Recipe spy1 = spy(Recipe.class);
        spy1.setId(idsToAdd.get(0));
        spyRecipes.add(spy1);
        final Recipe spy2 = spy(Recipe.class);
        spy2.setId(idsToAdd.get(1));
        spyRecipes.add(spy2);
        final Recipe spy3 = spy(Recipe.class);
        spy3.setId(idsToAdd.get(2));
        spyRecipes.add(spy3);

        //when
        when(recipeService.findByIds(categoryDTOMock.getRecipesId())).thenReturn(spyRecipes);
        final Category returnedCategory = categoryConverterFromDT.convert(categoryDTOMock);

        //then
        assertEquals(ID, returnedCategory.getId());
        assertEquals(DESCRIPTION, returnedCategory.getDescription());
        assertEquals(IDS.size(), returnedCategory.getRecipes().size());
    }
}