package guru.springframework.service;

import guru.springframework.model.Recipe;
import guru.springframework.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;
    @Mock
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void findAll() {
        Recipe recipe1 = new Recipe(1L);
        Recipe recipe2 = new Recipe(2L);
        Set<Recipe> toReturn = new HashSet<>(Set.of(recipe1, recipe2));

        Mockito.when(recipeService.findAll()).thenReturn(toReturn);

        final Set<Recipe> allRecipes = recipeService.findAll();
        assertEquals(2, allRecipes.size());
        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }
}