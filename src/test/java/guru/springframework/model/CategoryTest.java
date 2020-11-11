package guru.springframework.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        Assert.isNull(category.getId(), "ID should be null, when not set");
        Long id = 5l;
        category.setId(id);
        assertEquals(id, category.getId());
    }

    @Test
    void getDescription() {
        Assert.isNull(category.getDescription(), "description should be null, when not set");
        String description = "description";
        category.setDescription(description);
        assertEquals(description, category.getDescription());
    }

    @Test
    void getRecipes() {
        Assert.isTrue(category.getRecipes().isEmpty(), "recipes should be empty, when nothing added");
        final Recipe recipe1 = new Recipe(1L);
        final Recipe recipe2 = new Recipe(2L);
        Set<Recipe> recipes = new HashSet<>(Set.of(recipe1, recipe2));
        category.setRecipes(recipes);
        Assert.notEmpty(recipes, "recipes should be not empty, when something added");
        recipes.clear();
        category.setRecipes(recipes);
        Assert.isTrue(recipes.isEmpty(), "recipes should be empty when the collection was cleared");
    }
}