package guru.springframework.service;

import guru.springframework.model.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasure> findAll();
    void save(UnitOfMeasure unitOfMeasureToSave);
    void save(Set<UnitOfMeasure> unitsOfMeasureToSave);
    UnitOfMeasure findByDescription(String description);
}
