package guru.springframework.dto;

import guru.springframework.model.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {

    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;
    private Set<IngredientDTO> ingredientsDTO = new HashSet<>();
    private byte[] images;
    private String base64Image;
    private NotesDTO notesDTO;
    private Set<CategoryDTO> categoriesDTO = new HashSet<>();
}
