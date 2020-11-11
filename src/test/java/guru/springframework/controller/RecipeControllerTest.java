package guru.springframework.controller;

import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecipeControllerTest {

    private RecipeController recipeController;
    @Mock
    private RecipeService recipeService;
    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeController = new RecipeController(recipeService);
    }

    @Test
    void findAll() {
        //given
        Set<Recipe> recipesMockToReturn = new HashSet<>(Set.of(new Recipe(1L), new Recipe(2L)));

        //when
        when(recipeService.findAll()).thenReturn(recipesMockToReturn);

        //then
        String returnedUrlPath = recipeController.findAll(model);
        assertEquals("/recipe/index", returnedUrlPath);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        verify(recipeService, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        assertEquals(2, argumentCaptor.getValue().size());
    }
}