package guru.springframework.controller;

import guru.springframework.model.Recipe;
import guru.springframework.service.ImageService;
import guru.springframework.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({MockitoExtension.class})
class ImageControllerTest {

    @Mock
    private RecipeService recipeServiceMock;
    @Mock
    private ImageService imageServiceMock;
    @InjectMocks
    private ImageController imageController;

    @Mock
    private Model model;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void showUploadForm() throws Exception {
        //given
        Long id = 123L;
        final Recipe recipeMock = mock(Recipe.class);

        //when
        when(recipeServiceMock.findById(anyLong())).thenReturn(recipeMock);

        //then
        mockMvc.perform(get(String.format("/recipe/%d/image", id)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipe", recipeMock))
                .andExpect(view().name("recipe/imageuploadform"));

        verify(recipeServiceMock, times(1)).findById(anyLong());
    }

    @Test
    void handleImagePost() throws Exception {
        //given
        Long id = 222L;
        final MockMultipartFile mockMultipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Spring Framework Guru".getBytes());

        //when

        //then
        mockMvc.perform(multipart(String.format("/recipe/%d/image", id)).file(mockMultipartFile))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(String.format("/recipe/%d/show", id)))
        .andExpect(header().string("Location", String.format("/recipe/%d/show", id)));

        verify(imageServiceMock, times(1)).saveImageFile(anyLong(), any());
    }
}