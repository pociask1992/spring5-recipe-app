package guru.springframework.service;

import guru.springframework.model.Recipe;
import guru.springframework.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        log.info("ImageServiceImpl.saveImageFile - starting");
        final Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isPresent()) {
            try {
                final Byte[] bytesToSave = convertToBytesArray(file.getBytes());

                final Recipe recipe = recipeOptional.get();
                recipe.setImages(bytesToSave);

                recipeRepository.save(recipe);
            } catch (IOException e) {
                log.error(String.format("Problem with saving image. Recipe id:%d", recipeId), e);
                e.printStackTrace();
            }
        } else {
            log.error(String.format("ImageServiceImpl.saveImageFile Recipe with id: %d not found.", recipeId));
        }
        log.info("ImageServiceImpl.saveImageFile - ending");
    }

    private Byte[] convertToBytesArray(byte[] fileBytes) {
        final Byte[] bytesToSave = new Byte[fileBytes.length];
        int counter = 0;
        for (byte localByte : fileBytes) {
            bytesToSave[counter++] = localByte;
        }
        return bytesToSave;
    }
}
