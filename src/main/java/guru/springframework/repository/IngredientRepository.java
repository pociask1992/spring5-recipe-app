package guru.springframework.repository;

import guru.springframework.model.Ingredient;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    Iterable<Ingredient> findByRecipe_Id(Long recipeId, Sort sort);
}
