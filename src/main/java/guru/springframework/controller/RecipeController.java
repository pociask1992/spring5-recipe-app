package guru.springframework.controller;

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

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"", "/", "index", "index.html"})
    public String findAll(Model model) {
        final Set<Recipe> recipes = recipeService.findAll();
        model.addAttribute("recipes", recipes);
        return "/recipe/index";
    }

    @GetMapping("/findById/{id}")
    public String findById(Model model, @PathVariable("id") Long recipeId) {
        model.addAttribute("recipe", recipeService.findById(recipeId));
        return "/recipe/detailed_recipe";
    }
}
