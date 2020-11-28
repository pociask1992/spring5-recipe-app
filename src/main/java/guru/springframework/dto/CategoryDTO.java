package guru.springframework.dto;

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
public class CategoryDTO {
    private Long id;
    private String description;
    private Set<Long> recipesId = new HashSet<>();

    public void addRecipeId(Long idToAdd) {
        recipesId.add(idToAdd);
    }
}
