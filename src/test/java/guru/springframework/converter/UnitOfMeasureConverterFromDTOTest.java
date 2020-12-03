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
class UnitOfMeasureConverterFromDTOTest {

    @Spy
    private UnitOfMeasureDTO unitOfMeasureDTOSpy;
    @InjectMocks
    private UnitOfMeasureConverterFromDTO unitOfMeasureConverterFromDTO;

    private final Long ID = 199L;
    private final String DESCRIPTION = "UOM description";

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