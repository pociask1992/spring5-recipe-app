package guru.springframework.service;

import guru.springframework.model.Recipe;
import guru.springframework.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
        final Iterable<Recipe> allRecipes = recipeRepository.findAll();
        Set<Recipe> toReturn = new HashSet<>();
        if (Optional.ofNullable(allRecipes).isPresent()) {
//            allRecipes.forEach(localRecipe -> {
//                        saveImage(localRecipe);
//                    }
//            );
            allRecipes.spliterator().forEachRemaining(toReturn::add);
        }
        return toReturn;
    }


    private void saveImage(Recipe recipe) {
        if (Optional.ofNullable(recipe).isPresent()) {
            final byte[] images = recipe.getImages();
            if (Optional.ofNullable(images).isEmpty()) {
                int recid = recipe.getId().intValue();
                final byte[] readBytes = readImageByName(String.valueOf(recid));
                if (Optional.ofNullable(readBytes).isPresent() && readBytes.length > 0) {
                    recipe.setImages(readBytes);
                    recipeRepository.save(recipe);
                }
            }
        }
    }

    @Override
    public byte[] readImageByName(String imageName) {
        final Resource resource = resourceLoader.getResource(String.format("classpath:fotos/%s.jpg", imageName));
        byte[] readBytes = null;
        try {
            final File file = resource.getFile();
            readBytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readBytes;
    }

    @Override
    public void save(Recipe recipeToSave) {
        recipeRepository.save(recipeToSave);
    }

    @Override
    public Iterable<Recipe> save(Set<Recipe> recipesToSave) {
        return recipeRepository.saveAll(recipesToSave);
    }
}
