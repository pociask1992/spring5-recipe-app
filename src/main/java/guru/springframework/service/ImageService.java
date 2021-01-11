package guru.springframework.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(Long recipeId, MultipartFile file);
    void saveImageFileByteArray(Long recipeId, byte[] byteToSave);
}
