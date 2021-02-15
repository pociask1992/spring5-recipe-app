package guru.springframework.service;

import guru.springframework.exception.NotFoundException;
import guru.springframework.model.Recipe;
import guru.springframework.repository.RecipeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;
    private RecipeServiceImpl recipeService;

    private AutoCloseable autoCloseable;
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this  );

        recipeService = new RecipeServiceImpl(recipeRepository);
    }
    @AfterEach
    void releaseMock() throws Exception {
        autoCloseable.close();
    }

    @Test
    void findAll() {
        Recipe recipe1 = new Recipe(1L);
        Recipe recipe2 = new Recipe(2L);
        Set<Recipe> toReturn = new HashSet<>(Set.of(recipe1, recipe2));

        when(recipeService.findAll()).thenReturn(toReturn);

        final Set<Recipe> allRecipes = recipeService.findAll();
        assertEquals(2, allRecipes.size());
        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findByIdsWhenFoundRecipes() {
        //given
        Set<Recipe> recipes = Set.of(new Recipe(1L), new Recipe(2L), new Recipe(3L));
        Collection<Long> mockIDs = mock(Collection.class);

        //when
        when(mockIDs.isEmpty()).thenReturn(false);
        when(recipeRepository.findAllById(mockIDs)).thenReturn(recipes);
        final Set<Recipe> returnedRecipes = recipeService.findByIds(mockIDs);

        //then
        assertEquals(recipes.size(), returnedRecipes.size());
    }

    @Test
    void findByIdsWhenNotFoundRecipes() {
        //given
        Collection<Long> mockIDs = mock(Collection.class);

        //when
        when(mockIDs.isEmpty()).thenReturn(true);
        final Set<Recipe> returnedRecipes = recipeService.findByIds(mockIDs);

        //then
        assertTrue(returnedRecipes.isEmpty());
    }

    @Test
    void deleteById() {
        //given
        Long id = 100L;

        //when
        //then
        recipeService.deleteById(id);
        verify(recipeRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteAll() {
        //given
        //when
        //then
        recipeService.deleteAll();
        verify(recipeRepository, times(1)).deleteAll();
    }

    @Test
    void findByIdWhenRecipeFound() {
        //given
        final Recipe recipeSpy = spy(Recipe.class);
        Long recipeId = 123L;
        recipeSpy.setId(recipeId);

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipeSpy));

        //then
        final Recipe foundRecipe = recipeService.findById(recipeId);
        assertEquals(recipeSpy, foundRecipe);
    }

    @Test
    void findByIdWhenRecipeIdIsNull() {
        assertThrows(RuntimeException.class,
                () -> recipeService.findById(null));
    }

    @Test
    void findByIdWhenRecipeNotFound() {
        //given
        //when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class,
                () -> recipeService.findById(anyLong()));
    }
}