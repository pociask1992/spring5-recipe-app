package guru.springframework.repository;

import guru.springframework.dataloader.DefaultDataLoader;
import guru.springframework.model.UnitOfMeasure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitOfMeasureRepositoryITest {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    private DefaultDataLoader dataLoader;

    @Test
    public void findByDescriptionPieces() {
        String description = "Piece(s)";
        final UnitOfMeasure byDescription = unitOfMeasureRepository.findByDescription(description);
        assertEquals(description, byDescription.getDescription());
    }

    @Test
    public void findByDescriptionTeaspoons() {
        String description = "Teaspoon(s)";
        final UnitOfMeasure byDescription = unitOfMeasureRepository.findByDescription(description);
        assertEquals(description, byDescription.getDescription());
    }

    @Test
    public void findByDescriptionDashes() {
        String description = "Dash(es)";
        final UnitOfMeasure byDescription = unitOfMeasureRepository.findByDescription(description);
        assertEquals(description, byDescription.getDescription());
    }
}