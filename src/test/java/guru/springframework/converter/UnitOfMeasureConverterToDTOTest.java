package guru.springframework.converter;

import guru.springframework.dto.UnitOfMeasureDTO;
import guru.springframework.model.UnitOfMeasure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitOfMeasureConverterToDTOTest {

    @Spy
    private UnitOfMeasure unitOfMeasureSpy;
    @InjectMocks
    private UnitOfMeasureConverterToDTO unitOfMeasureConverterToDTO;

    private final Long ID = 100L;
    private final String DESCRIPTION = "UOM description";

    private AutoCloseable autoCloseable;
    @BeforeEach
    void openMock() {
        autoCloseable = MockitoAnnotations.openMocks(this  );
    }

    @AfterEach
    void releaseMock() throws Exception {
        autoCloseable.close();
    }
    @Test
    void convert() {
        //given
        unitOfMeasureSpy.setId(ID);
        unitOfMeasureSpy.setDescription(DESCRIPTION);
        //when
        final UnitOfMeasureDTO convertedObject = unitOfMeasureConverterToDTO.convert(unitOfMeasureSpy);
        //then
        assertEquals(ID, convertedObject.getId());
        assertEquals(DESCRIPTION, convertedObject.getDescription());
    }
}