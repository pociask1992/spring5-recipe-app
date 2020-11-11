package guru.springframework.controller;

import guru.springframework.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

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
        String returnedUrlPath = recipeController.findAll(model);
        assertEquals("/recipe/index", returnedUrlPath);

        verify(recipeService, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("recipes"), anyMap());
    }
}