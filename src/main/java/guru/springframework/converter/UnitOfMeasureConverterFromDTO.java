package guru.springframework.converter;

import guru.springframework.dto.UnitOfMeasureDTO;
import guru.springframework.model.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UnitOfMeasureConverterFromDTO implements Converter<UnitOfMeasureDTO, UnitOfMeasure> {

    @Override
    public UnitOfMeasure convert(UnitOfMeasureDTO unitOfMeasureDTO) {
        UnitOfMeasure toReturn = new UnitOfMeasure();
        if(Optional.ofNullable(unitOfMeasureDTO).isPresent()) {
            toReturn.setId(unitOfMeasureDTO.getId());
            toReturn.setDescription(unitOfMeasureDTO.getDescription());
        }
        return toReturn;
    }
}
