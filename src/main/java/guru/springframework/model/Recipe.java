package guru.springframework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"ingredients", "categories", "notes"})
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
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;
    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();


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

    public void addCategories(Set<Category> categoryToAdd) {
        categoryToAdd.forEach(category -> {
            category.addRecipe(this);
        });
        categories.addAll(categoryToAdd);
    }
}
