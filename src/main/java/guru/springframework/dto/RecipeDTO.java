package guru.springframework.dto;

import guru.springframework.model.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(100)
    private Integer servings;

    private String source;
    @URL
    private String url;

    @NotBlank
    private String directions;

    private Difficulty difficulty;
    private Set<IngredientDTO> ingredientsDTO = new HashSet<>();
    private Byte[] images;
    private NotesDTO notesDTO;
    private Set<CategoryDTO> categoriesDTO = new HashSet<>();

    @Builder
    public RecipeDTO(Long id) {
        this.id = id;
    }
}
