package guru.springframework.controller;

import guru.springframework.dto.IngredientDTO;
import guru.springframework.dto.UnitOfMeasureDTO;
import guru.springframework.service.IngredientService;
import guru.springframework.service.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({MockitoExtension.class})
class IngredientControllerTest {

    @Mock
    private IngredientService ingredientService;
    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    @Mock
    private Model model;
    MockMvc mockMvc;

    @InjectMocks
    private IngredientController ingredientController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void showAll() throws Exception {
        //given
        Long recipeId = 100L;
        final IngredientDTO spyIngredientDTO1 = spy(IngredientDTO.class);
        spyIngredientDTO1.setRecipeId(recipeId);
        final IngredientDTO spyIngredientDTO2 = spy(IngredientDTO.class);
        spyIngredientDTO2.setRecipeId(recipeId);
        Set<IngredientDTO> ingredientDTOSet = Set.of(spyIngredientDTO1, spyIngredientDTO2);

        //when
        when(ingredientService.findByRecipeId(anyLong())).thenReturn(ingredientDTOSet);

        //then
        mockMvc.perform(get(String.format("/recipe/%d/ingredient/show", recipeId)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredients"))
                .andExpect(model().attribute("ingredients", ingredientDTOSet))
                .andExpect(view().name("ingredients/show_recipe_ingredients_list"));
    }

    @Test
    void showIngredientForRecipe() throws Exception {
        //given
        Long recipeId = 235L;
        Long ingredientId = 1000L;
        final IngredientDTO spyIngredientDTO = spy(IngredientDTO.class);
        spyIngredientDTO.setRecipeId(recipeId);
        spyIngredientDTO.setId(ingredientId);

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(spyIngredientDTO);

        //then
        mockMvc.perform(get(String.format("/recipe/%d/ingredient/%d/show", recipeId, ingredientId)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attribute("ingredient", spyIngredientDTO))
                .andExpect(view().name("ingredients/show_ingredient"));
    }

    @Test
    void showUpdateFormIngredientForRecipe() throws Exception {
        //given
        Long recipeId = 123L;
        Long ingredientId = 1L;
        final IngredientDTO spyIngredientDTO = spy(IngredientDTO.class);
        spyIngredientDTO.setId(ingredientId);
        spyIngredientDTO.setRecipeId(recipeId);
        final UnitOfMeasureDTO spyUOM1 = spy(UnitOfMeasureDTO.class);
        final UnitOfMeasureDTO spyUOM2 = spy(UnitOfMeasureDTO.class);
        final UnitOfMeasureDTO spyUOM3 = spy(UnitOfMeasureDTO.class);
        final Set<UnitOfMeasureDTO> setUOM = Set.of(spyUOM1, spyUOM2, spyUOM3);

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(spyIngredientDTO);
        when(unitOfMeasureService.findAll()).thenReturn(setUOM);

        //then
        mockMvc.perform(get(String.format("/recipe/%d/ingredient/%d/update", recipeId, ingredientId)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient", "uomList"))
                .andExpect(model().attribute("ingredient", spyIngredientDTO))
                .andExpect(model().attribute("uomList", setUOM))
                .andExpect(view().name("ingredients/ingredient_form"));
    }

    @Test
    void updateIngredientForRecipe() throws Exception {
        //given
        Long recipeId = 1L;
        Long ingredientId = 222L;
        final IngredientDTO spyIngredientDTO = spy(IngredientDTO.class);
        spyIngredientDTO.setId(ingredientId);

        //when
        when(ingredientService.updateIngredientByRecipeIdAndIngredientId(any(), anyLong())).thenReturn(spyIngredientDTO);

        //then
        mockMvc.perform(post(String.format("/recipe/%d/ingredient", recipeId), spyIngredientDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("ingredients"))
                .andExpect(model().attribute("ingredients", spyIngredientDTO))
                .andExpect(redirectedUrl(String.format("/recipe/%d/ingredient/show", recipeId)));
    }

    @Test
    void deleteIngredientForRecipe() throws Exception {
        //given
        Long recipeId = 11L;
        Long ingredientId = 234L;
        //when

        //then
        mockMvc.perform(get(String.format("/recipe/%d/ingredient/%d/delete", recipeId, ingredientId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(String.format("/recipe/%d/ingredient/show", recipeId)));

        verify(ingredientService, times(1)).deleteIngredientByRecipeIdAndIngredientId(recipeId, ingredientId);
    }
}