package guru.springframework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        log.info("ImageServiceImpl.saveImageFile - starting");

        log.info("ImageServiceImpl.saveImageFile - ending");
    }
}
