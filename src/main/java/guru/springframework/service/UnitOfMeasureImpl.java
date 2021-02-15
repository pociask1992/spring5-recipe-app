package guru.springframework.service;

import guru.springframework.converter.UnitOfMeasureConverterToDTO;
import guru.springframework.dto.UnitOfMeasureDTO;
import guru.springframework.model.UnitOfMeasure;
import guru.springframework.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UnitOfMeasureImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureConverterToDTO unitOfMeasureConverterToDTO;

    public UnitOfMeasureImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                             UnitOfMeasureConverterToDTO unitOfMeasureConverterToDTO) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureConverterToDTO = unitOfMeasureConverterToDTO;
    }

    @Override
    public Set<UnitOfMeasureDTO> findAll() {
        log.debug("Invoking UnitOfMeasureImpl.findAll");
        final Iterable<UnitOfMeasure> foundUnitsOfMeasure = unitOfMeasureRepository.findAll();

        Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();
        foundUnitsOfMeasure.iterator().forEachRemaining(unitOfMeasureSet::add);
        return unitOfMeasureConverterToDTO.convertCollection(unitOfMeasureSet);
    }

    @Override
    public Optional<UnitOfMeasure> findById(Long id) {
        log.debug("Invoking UnitOfMeasureImpl.findById");
        return unitOfMeasureRepository.findById(id);
    }

    @Override
    public void save(UnitOfMeasure unitOfMeasureToSave) {
        log.debug("Invoking UnitOfMeasureImpl.save");
        unitOfMeasureRepository.save(unitOfMeasureToSave);
    }

    @Override
    public void save(Set<UnitOfMeasure> unitsOfMeasureToSave) {
        log.debug("Invoking UnitOfMeasureImpl.save");
        unitOfMeasureRepository.saveAll(unitsOfMeasureToSave);
    }

    @Override
    public UnitOfMeasure findByDescription(String description) {
        log.debug("Invoking UnitOfMeasureImpl.findByDescription");
        return unitOfMeasureRepository.findByDescription(description);
    }

    @Override
    public void deleteAll() {
        log.debug("Invoking UnitOfMeasureImpl.findByDescription");
        unitOfMeasureRepository.deleteAll();
    }
}


