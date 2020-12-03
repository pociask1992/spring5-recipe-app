package guru.springframework.converter;

import guru.springframework.dto.UnitOfMeasureDTO;
import guru.springframework.model.UnitOfMeasure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureConverterToDTOTest {

    @Spy
    private UnitOfMeasure unitOfMeasureSpy;
    @InjectMocks
    private UnitOfMeasureConverterToDTO unitOfMeasureConverterToDTO;

    private final Long ID = 100L;
    private final String DESCRIPTION = "UOM description";

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