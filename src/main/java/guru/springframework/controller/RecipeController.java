package guru.springframework.controller;

import guru.springframework.converter.RecipeConverterToDTO;
import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeConverterToDTO recipeConverterToDTO;
    public RecipeController(RecipeService recipeService,
                            RecipeConverterToDTO recipeConverterToDTO) {
        this.recipeService = recipeService;
        this.recipeConverterToDTO = recipeConverterToDTO;
    }

    @GetMapping({"", "/", "index", "index.html"})
    public String findAll(Model model) {
        final Set<Recipe> recipes = recipeService.findAll();
        model.addAttribute("recipes", recipeConverterToDTO.convert(recipes));
        return "/recipe/index";
    }

    @GetMapping("/findById/{id}")
    public String findById(Model model, @PathVariable("id") Long recipeId) {
        model.addAttribute("recipe", recipeConverterToDTO.convert(recipeService.findById(recipeId)));
        return "/recipe/detailed_recipe";
    }
}
