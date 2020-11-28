package guru.springframework.service;

import guru.springframework.model.UnitOfMeasure;

import java.util.Optional;
import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasure> findAll();
    Optional<UnitOfMeasure> findById(Long id);
    void save(UnitOfMeasure unitOfMeasureToSave);
    void save(Set<UnitOfMeasure> unitsOfMeasureToSave);
    UnitOfMeasure findByDescription(String description);
}
