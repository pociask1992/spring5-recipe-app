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

class UnitOfMeasureConverterFromDTOTest {

    @Spy
    private UnitOfMeasureDTO unitOfMeasureDTOSpy;
    @InjectMocks
    private UnitOfMeasureConverterFromDTO unitOfMeasureConverterFromDTO;

    private final Long ID = 199L;
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
        unitOfMeasureDTOSpy.setId(ID);
        unitOfMeasureDTOSpy.setDescription(DESCRIPTION);

        //when
        final UnitOfMeasure convertedObject = unitOfMeasureConverterFromDTO.convert(unitOfMeasureDTOSpy);

        //then
        assertEquals(ID, convertedObject.getId());
        assertEquals(DESCRIPTION, convertedObject.getDescription());
    }
}