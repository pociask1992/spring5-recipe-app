package guru.springframework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotesDTO {
    private Long id;
    private Long recipeId;
    private String recipeNotes;
}
