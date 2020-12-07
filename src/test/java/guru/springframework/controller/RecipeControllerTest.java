package guru.springframework.controller;

import guru.springframework.converter.RecipeConverterFromDTO;
import guru.springframework.converter.RecipeConverterToDTO;
import guru.springframework.dto.RecipeDTO;
import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({MockitoExtension.class})
class RecipeControllerTest {

    @Mock
    private RecipeConverterToDTO recipeConverterToDTO;
    @Mock
    private RecipeConverterFromDTO recipeConverterFromDTO;
    @Mock
    private RecipeService recipeService;


    @Mock
    private Model model;
    MockMvc mockMvc;


    @InjectMocks
    private RecipeController recipeController;

    @BeforeEach
    void setUp() {
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
        Long id1 = 1L;
        Long id2 = 2L;
        final HashSet<Recipe> recipesSpy = spy(new HashSet<>());
        final Recipe recipe1Spy = spy(Recipe.class);
        recipe1Spy.setId(id1);
        recipesSpy.add(recipe1Spy);
        final Recipe recipe2Spy = spy(Recipe.class);
        recipe2Spy.setId(id2);
        recipesSpy.add(recipe2Spy);

        final HashSet<RecipeDTO> recipesDTOSpy = spy(new HashSet<>());
        final RecipeDTO recipeDTO1Spy = spy(RecipeDTO.class);
        recipeDTO1Spy.setId(id1);
        recipesDTOSpy.add(recipeDTO1Spy);
        final RecipeDTO recipeDTO2Spy = spy(RecipeDTO.class);
        recipeDTO2Spy.setId(id2);
        recipesDTOSpy.add(recipeDTO2Spy);

        //when
        when(recipeService.findAllOrderByDescriptionDescAndIdAsc()).thenReturn(recipesSpy);
        when(recipeConverterToDTO.convertCollection(anyCollection())).thenReturn(recipesDTOSpy);
        //then
        String returnedUrlPath = recipeController.findAll(model);
        assertEquals("/recipe/index", returnedUrlPath);

        ArgumentCaptor<Set<RecipeDTO>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        verify(recipeService, times(1)).findAllOrderByDescriptionDescAndIdAsc();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        assertEquals(2, argumentCaptor.getValue().size());
    }

    @Test
    void findByIdWhenFound() {
        //given
        Long id = 1L;
        Recipe recipeSpy = spy(Recipe.class);
        recipeSpy.setId(id);
        RecipeDTO recipeDTOSpy = spy(RecipeDTO.class);
        recipeDTOSpy.setId(id);

        //when
        when(recipeService.findById(id)).thenReturn(recipeSpy);
        when(recipeConverterToDTO.convert(recipeSpy)).thenReturn(recipeDTOSpy);
        String returnedView = recipeController.findById(model, id);
        ArgumentCaptor<RecipeDTO> recipeArgumentCaptor = ArgumentCaptor.forClass(RecipeDTO.class);

        verify(recipeService, times(1)).findById(anyLong());
        verify(model, times(1)).addAttribute(eq("recipe"), recipeArgumentCaptor.capture());

        //then
        assertEquals(id, recipeArgumentCaptor.getValue().getId());
        assertEquals("/recipe/detailed_recipe", returnedView);
    }

    @Test
    void findByIdWhenDoesNotFound() {
        //given
        RecipeDTO recipeDTOSpy = spy(RecipeDTO.class);

        //when
        when(recipeService.findById(anyLong())).thenReturn(null);
        when(recipeConverterToDTO.convert(null)).thenReturn(recipeDTOSpy);
        String returnedView = recipeController.findById(model, anyLong());
        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        verify(recipeService, times(1)).findById(anyLong());
        verify(model, times(1)).addAttribute(eq("recipe"), recipeArgumentCaptor.capture());

        //then
        assertEquals(recipeDTOSpy, recipeArgumentCaptor.getValue());
        assertEquals("/recipe/detailed_recipe", returnedView);
    }

    @Test
    void findByIdWebMvcWhenFound() throws Exception {
        //given
        Long id = 1L;
        final Recipe recipeSpy = spy(Recipe.class);
        recipeSpy.setId(id);
        final RecipeDTO recipeDTOSpy = spy(RecipeDTO.class);
        recipeDTOSpy.setId(id);

        //when
        when(recipeService.findById(anyLong())).thenReturn(recipeSpy);
        when(recipeConverterToDTO.convert(recipeSpy)).thenReturn(recipeDTOSpy);

        //then
        mockMvc.perform(get("/recipe/{id}/show", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/detailed_recipe"))
                .andExpect(model().attribute("recipe", recipeDTOSpy));
    }

    @Test
    void findByIdWebMvcWhenDoesNotFind() throws Exception {
        //given
        final RecipeDTO recipeDTOSpy = spy(RecipeDTO.class);

        //when
        when(recipeService.findById(anyLong())).thenReturn(null);
        when(recipeConverterToDTO.convert(null)).thenReturn(recipeDTOSpy);

        //then
        mockMvc.perform(get("/recipe/{id}/show", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/detailed_recipe"))
                .andExpect(model().attribute("recipe", recipeDTOSpy));
    }

    @Test
    void updateWebMvcWhenFound() throws Exception {
        //given
        Long id = 1L;
        final Recipe recipeSpy = spy(Recipe.class);
        recipeSpy.setId(id);
        final RecipeDTO recipeDTOSpy = spy(RecipeDTO.class);
        recipeDTOSpy.setId(id);

        //when
        when(recipeService.findById(anyLong())).thenReturn(recipeSpy);
        when(recipeConverterToDTO.convert(recipeSpy)).thenReturn(recipeDTOSpy);

        //then
        mockMvc.perform(get("/recipe/{id}/update", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/recipe_form"))
                .andExpect(model().attribute("recipe", recipeDTOSpy));
    }

    @Test
    void updateWebMvcWhenDoesNotFind() throws Exception {
        //given
        final RecipeDTO recipeDTOSpy = spy(RecipeDTO.class);

        //when
        when(recipeService.findById(anyLong())).thenReturn(null);
        when(recipeConverterToDTO.convert(null)).thenReturn(recipeDTOSpy);

        //then
        mockMvc.perform(get("/recipe/{id}/update", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/recipe_form"))
                .andExpect(model().attribute("recipe", recipeDTOSpy));
    }

    @Test
    void showNewRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe_form"))
                .andExpect(model().attribute("recipe", isA(RecipeDTO.class)));
    }

    @Test
    void saveOrUpdateRecipe() throws Exception {
        //given
        Long recipeId = 100L;
        final RecipeDTO recipeDTOSpy = spy(RecipeDTO.class);
        recipeDTOSpy.setId(recipeId);

        final Recipe convertedRecipeSpy = spy(Recipe.class);
        convertedRecipeSpy.setId(recipeId);

        final Recipe savedRecipeSpy = spy(Recipe.class);
        savedRecipeSpy.setId(recipeId);

        //when
        when(recipeConverterFromDTO.convert(any())).thenReturn(convertedRecipeSpy);
        when(recipeService.save(convertedRecipeSpy)).thenReturn(savedRecipeSpy);

        //then
        mockMvc.perform(post("/recipe", recipeDTOSpy))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl(String.format("/recipe/%d/show", recipeId)));
    }

    @Test
    void deleteById() throws Exception {
        //given
        Long id = 1L;
        //when
        //then

        mockMvc.perform(get(String.format("/recipe/%d/delete", id)))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/recipe/"));

        verify(recipeService, times(1)).deleteById(anyLong());
    }
}