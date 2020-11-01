package guru.springframework.service;

import guru.springframework.model.UnitOfMeasure;
import guru.springframework.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UnitOfMeasureImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasureImpl(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Set<UnitOfMeasure> findAll() {
        final Iterable<UnitOfMeasure> foundUnitsOfMeasure = unitOfMeasureRepository.findAll();

        Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();
        foundUnitsOfMeasure.iterator().forEachRemaining(unitOfMeasureSet::add);
        return unitOfMeasureSet;
    }

    @Override
    public void save(UnitOfMeasure unitOfMeasureToSave) {
        unitOfMeasureRepository.save(unitOfMeasureToSave);
    }

    @Override
    public void save(Set<UnitOfMeasure> unitsOfMeasureToSave) {
        unitOfMeasureRepository.saveAll(unitsOfMeasureToSave);
    }

    @Override
    public UnitOfMeasure findByDescription(String description) {
        return unitOfMeasureRepository.findByDescription(description);
    }
}


