package guru.springframework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {

    private Long id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureDTO unitOfMeasureDTO;
    private Long recipeId;
}
