package guru.springframework.converter;

import guru.springframework.dto.UnitOfMeasureDTO;
import guru.springframework.model.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class UnitOfMeasureConverterToDTO implements Converter<UnitOfMeasure, UnitOfMeasureDTO> {

    @Override
    public UnitOfMeasureDTO convert(UnitOfMeasure unitOfMeasure) {
        UnitOfMeasureDTO toReturn = new UnitOfMeasureDTO();
        if(Optional.ofNullable(unitOfMeasure).isPresent()) {
            toReturn.setId(unitOfMeasure.getId());
            toReturn.setDescription(unitOfMeasure.getDescription());
        }
        return toReturn;
    }

    public Set<UnitOfMeasureDTO> convertCollection(Collection<UnitOfMeasure> toConvert) {
        Set<UnitOfMeasureDTO> toReturn = new HashSet<>();
        toConvert.forEach(elemToConvert -> toReturn.add(convert(elemToConvert)));
        return toReturn;
    }
}
