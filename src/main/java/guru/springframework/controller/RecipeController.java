package guru.springframework.controller;

import guru.springframework.converter.RecipeConverterFromDTO;
import guru.springframework.converter.RecipeConverterToDTO;
import guru.springframework.dto.RecipeDTO;
import guru.springframework.exception.NotFoundException;
import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import guru.springframework.validator.ArgumentValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.ArrayUtils;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeConverterToDTO recipeConverterToDTO;
    private final RecipeConverterFromDTO recipeConverterFromDTO;

    private final String RECIPE_INDEX_VIEW = "/recipe/index";
    private final String RECIPE_DETAILED_RECIPE_VIEW = "/recipe/detailed_recipe";
    private final String RECIPE_RECIPE_FORM_VIEW = "/recipe/recipe_form";
    private final String RECIPE_REDIRECT_RECIPE_SHOW = "redirect:/recipe/%d/show";
    private final String RECIPE_REDIRECT_RECIPE =  "redirect:/recipe/";

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
        return RECIPE_INDEX_VIEW;
    }

    @GetMapping("/{id}/show")
    public String findById(Model model, @PathVariable("id") String recipeId) {
        Long recipeIdLong = ArgumentValidator.convertStringToLong(recipeId, String.format("Cannot convert to Long. For input string: %s", recipeId));
        final Recipe foundRecipe = recipeService.findById(recipeIdLong);
        final RecipeDTO convertedRecipe = recipeConverterToDTO.convert(foundRecipe);
        model.addAttribute("recipe", convertedRecipe);
        return RECIPE_DETAILED_RECIPE_VIEW;
}

    @GetMapping("/{id}/update")
    public String update(Model model, @PathVariable("id") String recipeId) {
        Long recipeIdLong = ArgumentValidator.convertStringToLong(recipeId, String.format("Cannot convert to Long. For input string: %s", recipeId));
        final Recipe foundRecipe = recipeService.findById(recipeIdLong);
        final RecipeDTO convertedRecipe = recipeConverterToDTO.convert(foundRecipe);
        model.addAttribute("recipe", convertedRecipe);
        return RECIPE_RECIPE_FORM_VIEW;
    }

    @GetMapping("/new")
    public String showNewRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeDTO());
        return RECIPE_RECIPE_FORM_VIEW;
    }

    @PostMapping
    public String saveOrUpdateRecipe(@Valid @ModelAttribute("recipe") RecipeDTO recipeDTO, BindingResult bindingResult) {
        String viewToReturn;
        if(bindingResult.hasErrors()) {
            final List<ObjectError> allErrors = bindingResult.getAllErrors();
            allErrors.forEach(objectError -> {
                prepareErrorMessage(objectError);
            });
            viewToReturn = RECIPE_RECIPE_FORM_VIEW;
        } else {
            final Recipe convertRecipe = recipeConverterFromDTO.convert(recipeDTO);

            final Recipe savedRecipe = recipeService.save(convertRecipe);

            viewToReturn = String.format(RECIPE_REDIRECT_RECIPE_SHOW, savedRecipe.getId());
        }
        return viewToReturn;
    }

    private void prepareErrorMessage(ObjectError objectError) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Error on object:");
        errorMessage.append(objectError.getObjectName());
        final Object[] objectErrorArguments = objectError.getArguments();
        if(!ArrayUtils.isEmpty(objectErrorArguments)) {
            Arrays.asList(objectErrorArguments).forEach(objectErrorArgument -> {
                errorMessage.append("Argument:");
                errorMessage.append(objectErrorArgument);
                errorMessage.append("\n");
            });
        }
        log.error(errorMessage.toString());
    }

    @GetMapping("/{id}/delete")
    public String deleteById(@PathVariable String id) {
        Long recipeIdLong = ArgumentValidator.convertStringToLong(id, String.format("Cannot convert to Long. For input string: %s", id));
        recipeService.deleteById(recipeIdLong);

        return RECIPE_REDIRECT_RECIPE;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
