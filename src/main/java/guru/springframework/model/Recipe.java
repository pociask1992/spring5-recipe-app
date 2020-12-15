package guru.springframework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"ingredients", "categories", "notes"})
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();
    @Lob
    private byte[] images;
    private String base64Image;
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;
    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();


    public Recipe(Long id) {
        this.id = id;
    }

    public Notes addNotes(Notes notesToAdd) {
        this.setNotes(notesToAdd);
        notesToAdd.setRecipe(this);
        return notesToAdd;
    }


    public void addIngredients(Set<Ingredient> ingredientsToSave) {
        ingredientsToSave.forEach(ingredient -> {
            ingredient.setRecipe(this);
        });
        ingredients.addAll(ingredientsToSave);
    }

    public void addIngredient(Ingredient ingredientToAdd) {
        ingredientToAdd.setRecipe(this);
        ingredients.add(ingredientToAdd);
    }

    public void removeIngredient(Ingredient ingredientToDelete) {
        ingredientToDelete.setRecipe(null);
        ingredients.remove(ingredientToDelete);
    }

    public void addCategories(Set<Category> categoriesToAdd) {
        categories.addAll(categoriesToAdd);
    }

    public void addCategory(Category categoryToAdd) {
        categories.add(categoryToAdd);
    }
}
