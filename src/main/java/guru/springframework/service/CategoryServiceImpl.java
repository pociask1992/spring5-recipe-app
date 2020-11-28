package guru.springframework.service;

import guru.springframework.model.Category;
import guru.springframework.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Iterable<Category> findByIds(Collection<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }

    @Override
    public Category save(Category categoryToSave) {
        log.debug("Invoking CategoryServiceImpl.save");
        return categoryRepository.save(categoryToSave);
    }

    @Override
    public Category findByDescription(String description) {
        log.debug("Invoking CategoryServiceImpl.findByDescription");
        return categoryRepository.findByDescription(description);
    }
}
