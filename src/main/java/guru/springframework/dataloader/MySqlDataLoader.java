package guru.springframework.dataloader;

import guru.springframework.model.*;
import guru.springframework.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
@Profile({"dev", "prod"})
public class MySqlDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UnitOfMeasureService unitOfMeasureService;
    private final CategoryService categoryService;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final ImageService imageService;

    public MySqlDataLoader(UnitOfMeasureService unitOfMeasureService, CategoryService categoryService,
                             RecipeService recipeService, IngredientService ingredientService, ImageService imageService) {
        this.unitOfMeasureService = unitOfMeasureService;
        this.categoryService = categoryService;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.imageService = imageService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        clearDatabase();

        saveCategory();
        saveUnitOfMeasure();
        final Iterable<Recipe> savedRecipes = saveRecipes();
        saveImages(savedRecipes);

    }

    private void clearDatabase() {
        recipeService.deleteAll();
        unitOfMeasureService.deleteAll();
        categoryService.deleteAll();
    }

    private void saveImages(Iterable<Recipe> savedRecipes) {
        savedRecipes.forEach(recipe -> {
            final Long recipeId = recipe.getId();
            String description = recipe.getDescription();
            description = description.replace(" ", "");
            imageService.saveImageFile(recipeId, readFile(String.format("%s.jpg", description)));
        });
    }

    private Iterable<Recipe> saveRecipes() {
        return recipeService.save(getRecipes());
    }

    private Set<Recipe> getRecipes() {
        Set<Recipe> recipesToSave = Set.of(getGuacamole());

        return recipesToSave;
    }

    private Recipe getGuacamole() {
        log.info("DataLoader.getGuacamole");
        Recipe guacamole = new Recipe();
        guacamole.setDescription("Perfect Guacamole");
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setCookTime(10);
        guacamole.setPrepTime(10);
        guacamole.setServings(10);
        guacamole.setSource("https://www.simplyrecipes.com/");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.addNotes(getGuacamoleNotes());
        assignCategoryToGuacamole(guacamole);
        assignIngredientsToGuacamole(guacamole);

        return guacamole;
    }

    private MultipartFile readFile(String fileName) {
        MultipartFile multipartFileToReturn = null;
        String path = String.format("/static/images/%s", fileName);
        try {
            final URL resource = getClass().getResource(path);
            File file = Paths.get(resource.toURI()).toFile();
            FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
            InputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = fileItem.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            multipartFileToReturn = new CommonsMultipartFile(fileItem);

        } catch (FileNotFoundException e) {
            log.error(String.format("File not found, path:", path), e);
        } catch (IOException e) {
            log.error("IOException", e);
        } catch (URISyntaxException e) {
            log.error("URISyntaxException", e);
        }

        return multipartFileToReturn;
    }

    private void assignCategoryToGuacamole(Recipe recipe) {
        log.info("DataLoader.assignCategoryToGuacamole");
        final Category hotDish = categoryService.findByDescription("Hot dish");
        if (Optional.ofNullable(hotDish).isPresent()) {
            recipe.addCategory(hotDish);
        }
    }

    private void saveUnitOfMeasure() {
        log.info("DataLoader.saveUnitOfMeasure");
        UnitOfMeasure pieces = new UnitOfMeasure();
        pieces.setDescription("Piece(s)");

        UnitOfMeasure teaspoon = new UnitOfMeasure();
        teaspoon.setDescription("Teaspoon(s)");

        UnitOfMeasure tablespoon = new UnitOfMeasure();
        tablespoon.setDescription("Tablespoon(s)");

        UnitOfMeasure dash = new UnitOfMeasure();
        dash.setDescription("Dash(es)");

        Set<UnitOfMeasure> unitsOfMeasureToSave = Set.of(pieces, teaspoon, tablespoon, dash);
        unitOfMeasureService.save(unitsOfMeasureToSave);
    }

    private Notes getGuacamoleNotes() {
        log.info("DataLoader.getGuacamoleNotes");
        Notes notes = new Notes();
        notes.setRecipeNotes("All you really need to make guacamole is ripe avocados and salt. After that, a little lime or lemon juice a splash of acidity will help to balance the richness of the avocado." +
                " Then if you want, add chopped cilantro, chiles, onion, and/or tomato.");

        return notes;
    }

    private void saveCategory() {
        log.info("DataLoader.saveCategory");
        Category hotDish = new Category();
        hotDish.setDescription("Hot dish");

        categoryService.save(hotDish);
    }

    private void assignIngredientsToGuacamole(Recipe recipe) {
        log.info("DataLoader.assignIngredientsToGuacamole");
        Set<Ingredient> ingredients = new HashSet<>();

        Ingredient avocados = new Ingredient();
        avocados.setDescription("ripe avocados");
        avocados.setAmount(new BigDecimal(1));
        avocados.setUnitOfMeasure(getUnitOfMeasureByDescription("Piece(s)"));
        ingredients.add(avocados);

        Ingredient salt = new Ingredient();
        salt.setDescription("salt");
        salt.setAmount(new BigDecimal(0.25));
        salt.setUnitOfMeasure(getUnitOfMeasureByDescription("Teaspoon(s)"));
        ingredients.add(salt);

        Ingredient lime = new Ingredient();
        lime.setDescription("fresh lime juice or lemon juice");
        lime.setAmount(new BigDecimal(1));
        lime.setUnitOfMeasure(getUnitOfMeasureByDescription("Tablespoon(s)"));
        ingredients.add(lime);

        Ingredient onion = new Ingredient();
        onion.setDescription("to 1/4 cup of minced red onion");
        onion.setAmount(new BigDecimal(2));
        onion.setUnitOfMeasure(getUnitOfMeasureByDescription("Tablespoon(s)"));
        ingredients.add(onion);

        Ingredient chile = new Ingredient();
        chile.setDescription("serrano chiles, stems and seeds removed, minced");
        chile.setAmount(new BigDecimal(2));
        chile.setUnitOfMeasure(getUnitOfMeasureByDescription("Piece(s)"));
        ingredients.add(chile);

        Ingredient cilantro = new Ingredient();
        cilantro.setDescription("cilantro");
        cilantro.setAmount(new BigDecimal(2));
        cilantro.setUnitOfMeasure(getUnitOfMeasureByDescription("Teaspoon(s)"));
        ingredients.add(cilantro);

        Ingredient pepper = new Ingredient();
        pepper.setDescription("freshly grated black pepper");
        pepper.setAmount(new BigDecimal(2));
        pepper.setUnitOfMeasure(getUnitOfMeasureByDescription("Dash(es)"));
        ingredients.add(pepper);

        Ingredient tomato = new Ingredient();
        tomato.setDescription("ripe tomato");
        tomato.setAmount(new BigDecimal(1));
        tomato.setUnitOfMeasure(getUnitOfMeasureByDescription("Piece(s)"));
        ingredients.add(tomato);

        Ingredient radish = new Ingredient();
        radish.setDescription("red radish");
        radish.setAmount(new BigDecimal(1));
        radish.setUnitOfMeasure(getUnitOfMeasureByDescription("Piece(s)"));
        ingredients.add(radish);

        Ingredient tortilla = new Ingredient();
        tortilla.setDescription("tortilla chips");
        tortilla.setAmount(new BigDecimal(1));
        tortilla.setUnitOfMeasure(getUnitOfMeasureByDescription("Piece(s)"));
        ingredients.add(tortilla);

        recipe.addIngredients(ingredients);
    }

    private UnitOfMeasure getUnitOfMeasureByDescription(String description) {
        log.info("DataLoader.getUnitOfMeasureByDescription");
        return unitOfMeasureService.findByDescription(description);
    }
}
