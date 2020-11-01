package guru.springframework.service;

import guru.springframework.model.Category;

public interface CategoryService {

    Category save(Category categoryToSave);
    Category findByDescription(String description);
}
