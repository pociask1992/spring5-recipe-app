package guru.springframework.controller;

import guru.springframework.dto.IngredientDTO;
import guru.springframework.service.IngredientService;
import guru.springframework.service.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IngredientController {

    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredient/show")
    public String showAll(Model model, @PathVariable Long recipeId) {
        model.addAttribute("ingredients", ingredientService.findByRecipeId(recipeId));
        return "ingredients/show_recipe_ingredients_list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredientForRecipe(Model model, @PathVariable Long recipeId, @PathVariable Long ingredientId) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
        return "ingredients/show_ingredient";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String showUpdateFormIngredientForRecipe(Model model, @PathVariable Long recipeId, @PathVariable Long ingredientId) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
        model.addAttribute("uomList", unitOfMeasureService.findAll());
        return "ingredients/ingredient_form";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String updateIngredientForRecipe(Model model, @ModelAttribute IngredientDTO ingredientDTO,
        @PathVariable Long recipeId) {
        model.addAttribute("ingredients",
                ingredientService.updateIngredientByRecipeIdAndIngredientId(ingredientDTO, recipeId));
        return String.format("redirect:/recipe/%d/ingredient/show", recipeId);
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredientForRecipe(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
        ingredientService.deleteIngredientByRecipeIdAndIngredientId(recipeId, ingredientId);
        return String.format("redirect:/recipe/%d/ingredient/show", recipeId);
    }
}
