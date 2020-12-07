package guru.springframework.controller;

import guru.springframework.converter.RecipeConverterFromDTO;
import guru.springframework.converter.RecipeConverterToDTO;
import guru.springframework.dto.RecipeDTO;
import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeConverterToDTO recipeConverterToDTO;
    private final RecipeConverterFromDTO recipeConverterFromDTO;

    public RecipeController(RecipeService recipeService,
                            RecipeConverterToDTO recipeConverterToDTO,
                            RecipeConverterFromDTO recipeConverterFromDTO) {
        this.recipeService = recipeService;
        this.recipeConverterToDTO = recipeConverterToDTO;
        this.recipeConverterFromDTO = recipeConverterFromDTO;
    }

    @GetMapping({"", "/", "index", "index.html"})
    public String findAll(Model model) {
        final Set<Recipe> recipes = recipeService.findAllOrderByDescriptionDescAndIdAsc();
        final Set<RecipeDTO> convertedRecipes = recipeConverterToDTO.convertCollection(recipes);
        model.addAttribute("recipes", convertedRecipes);
        return "/recipe/index";
    }

    @GetMapping("/{id}/show")
    public String findById(Model model, @PathVariable("id") Long recipeId) {
        final Recipe foundRecipe = recipeService.findById(recipeId);
        final RecipeDTO convertedRecipe = recipeConverterToDTO.convert(foundRecipe);
        model.addAttribute("recipe", convertedRecipe);
        return "/recipe/detailed_recipe";
    }

    @GetMapping("/{id}/update")
    public String update(Model model, @PathVariable("id") Long recipeId) {
        final Recipe foundRecipe = recipeService.findById(recipeId);
        final RecipeDTO convertedRecipe = recipeConverterToDTO.convert(foundRecipe);
        model.addAttribute("recipe", convertedRecipe);
        return "/recipe/recipe_form";
    }

    @GetMapping("/new")
    public String showNewRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeDTO());
        return "recipe/recipe_form";
    }

    @PostMapping
    public String saveOrUpdateRecipe(@ModelAttribute RecipeDTO recipeDTO) {
        final Recipe convertRecipe = recipeConverterFromDTO.convert(recipeDTO);
        final Recipe savedRecipe = recipeService.save(convertRecipe);

        return String.format("redirect:/recipe/%d/show", savedRecipe.getId());
    }

    @GetMapping("/{id}/delete")
    public String deleteById(@PathVariable Long id) {
        recipeService.deleteById(id);

        return "redirect:/recipe/";
    }
}
