package guru.springframework.service;

import guru.springframework.exception.NotFoundException;
import guru.springframework.model.Recipe;
import guru.springframework.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private ResourceLoader resourceLoader;
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    @Override
    public Set<Recipe> findAll() {
        log.debug("Invoking RecipeServiceImpl.findAll");
        final Iterable<Recipe> allRecipes = recipeRepository.findAll();
        Set<Recipe> toReturn = new HashSet<>();
        if (Optional.ofNullable(allRecipes).isPresent()) {
            allRecipes.spliterator().forEachRemaining(toReturn::add);
        }
        return toReturn;
    }

    @Override
    public Set<Recipe> findAllOrderByDescriptionDescAndIdAsc() {
        Set<Recipe> toReturn = new LinkedHashSet<>();
        final Iterable<Recipe> recipes = recipeRepository.findAllByOrderByDescriptionDescIdAsc();
        recipes.spliterator().forEachRemaining(toReturn::add);
        return toReturn;
    }

    @Override
    public Recipe findById(Long recipeId) {
        log.debug(String.format("Invoking RecipeServiceImpl.findById(%d)", recipeId));
        Optional<Recipe> toReturn;
        if (Optional.ofNullable(recipeId).isPresent()) {
            toReturn = recipeRepository.findById(recipeId);;
        } else {
            throw new RuntimeException("RecipeServiceImpl.findById recipeId cannot be null.");
        }
        if(toReturn.isEmpty()) {
            throw new NotFoundException(String.format("Recipe not found for recipeId: %d", recipeId));
        }
        return toReturn.orElse(null);
    }

    @Override
    public Set<Recipe> findByIds(Collection<Long> ids) {
        Set<Recipe> toReturn = new HashSet<>();
        if(Optional.ofNullable(ids).isPresent() && !ids.isEmpty()) {
            final Iterable<Recipe> foundIds = recipeRepository.findAllById(ids);
            foundIds.forEach(toReturn::add);
        }
        return toReturn;
    }

    @Override
    public Recipe save(Recipe recipeToSave) {
        log.debug("RecipeServiceImpl.save");
        return recipeRepository.save(recipeToSave);
    }

    @Override
    public Iterable<Recipe> save(Set<Recipe> recipesToSave) {
        log.debug("RecipeServiceImpl.save");
        return recipeRepository.saveAll(recipesToSave);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("RecipeServiceImpl.deleteById");
        recipeRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        log.debug("RecipeServiceImpl.deleteAll");
        recipeRepository.deleteAll();
    }
}
