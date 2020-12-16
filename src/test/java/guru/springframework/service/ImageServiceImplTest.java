package guru.springframework.service;

import guru.springframework.model.Recipe;
import guru.springframework.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class ImageServiceImplTest {

    @Mock
    private RecipeRepository recipeRepositoryMock;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    void saveImageFile() throws IOException {
        //given
        Long id = 123L;
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Spring Framework Guru".getBytes());
        final Recipe recipeSpy = spy(Recipe.class);
        recipeSpy.setId(id);
        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        when(recipeRepositoryMock.findById(anyLong())).thenReturn(Optional.of(recipeSpy));
        imageService.saveImageFile(id, multipartFile);

        //then
        verify(recipeRepositoryMock, times(1)).save(recipeArgumentCaptor.capture());
        final Recipe savedRecipe = recipeArgumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImages().length);
    }
}