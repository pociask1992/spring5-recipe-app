package guru.springframework.controller;

import guru.springframework.model.Recipe;
import guru.springframework.service.ImageService;
import guru.springframework.service.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ArrayUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(Model model, @PathVariable Long id) {
        model.addAttribute("recipe", recipeService.findById(id));

        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(id, file);

        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("recipe/{recipeId}/recipeimage")
    public void renderImageFromDB(@PathVariable Long recipeId, HttpServletResponse response) throws IOException {
        final Optional<Recipe> recipeOptional = Optional.ofNullable(recipeService.findById(recipeId));

        if(recipeOptional.isPresent()) {
            final Recipe recipe = recipeOptional.get();
            final Byte[] images = recipe.getImages();
            if(!ArrayUtils.isEmpty(images)) {
                final byte[] bytesToAdd = new byte[images.length];
                int counter = 0;
                for(byte localByte : images) {
                    bytesToAdd[counter++] = localByte;
                }
                response.setContentType("image/jpeg");
                InputStream inputStream = new ByteArrayInputStream(bytesToAdd);
                IOUtils.copy(inputStream, response.getOutputStream());
            }
        }
    }
}
