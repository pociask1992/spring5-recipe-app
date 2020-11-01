package guru.springframework.service;

import guru.springframework.model.Category;
import guru.springframework.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category categoryToSave) {
        return categoryRepository.save(categoryToSave);
    }

    @Override
    public Category findByDescription(String description) {
        return categoryRepository.findByDescription(description);
    }
}
