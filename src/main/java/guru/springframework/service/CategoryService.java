package guru.springframework.service;

import guru.springframework.model.Category;

import java.util.Collection;

public interface CategoryService {

    Iterable<Category> findByIds(Collection<Long> categoryIds);
    Category save(Category categoryToSave);
    Category findByDescription(String description);
}
