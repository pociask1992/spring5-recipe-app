package guru.springframework.controller;

import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    private RecipeController recipeController;
    @Mock
    private RecipeService recipeService;
    @Mock
    private Model model;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void testWebMvc() throws Exception {
        mockMvc.perform(get("/recipe/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/index"));
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

    @Test
    void findByIdWhenFound() {
        //given
        Recipe recipe1 = new Recipe(1L);

        //when
        Long id  = 1L;
        when(recipeService.findById(anyLong())).thenReturn(recipe1);
        String returnedView = recipeController.findById(model, id);
        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        verify(recipeService, times(1)).findById(anyLong());
        verify(model, times(1)).addAttribute(eq("recipe"), recipeArgumentCaptor.capture());

        //then
        assertEquals(id, recipeArgumentCaptor.getValue().getId());
        assertEquals("/recipe/detailed_recipe", returnedView);
    }

    @Test
    void findByIdWhenDoesNotFound() {
        //given

        //when
        when(recipeService.findById(anyLong())).thenReturn(null);
        String returnedView = recipeController.findById(model, anyLong());
        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        verify(recipeService, times(1)).findById(anyLong());
        verify(model, times(1)).addAttribute(eq("recipe"), recipeArgumentCaptor.capture());

        //then
        assertNull(recipeArgumentCaptor.getValue());
        assertEquals("/recipe/detailed_recipe", returnedView);
    }

    @Test
    void findByIdWebMvcWhenFound() throws Exception {
        Recipe recipe1 = new Recipe(1L);

        when(recipeService.findById(anyLong())).thenReturn(recipe1);

        mockMvc.perform(get("/recipe/findById/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/detailed_recipe"))
                .andExpect(model().attribute("recipe", recipe1));
    }
    @Test
    void findByIdWebMvcWhenDoesNotFound() throws Exception {

        when(recipeService.findById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/recipe/findById/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/detailed_recipe"))
                .andExpect(model().attribute("recipe", IsNull.nullValue()));
    }
}