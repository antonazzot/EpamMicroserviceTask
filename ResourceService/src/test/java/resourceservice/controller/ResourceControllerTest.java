package resourceservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import resourceservice.exception.MyCustomAppException;
import resourceservice.model.Song;
import resourceservice.repository.SongRepository;
import resourceservice.service.AmazonService;
import resourceservice.service.FileValidator;
import resourceservice.service.KafkaService;
import resourceservice.service.SongService;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ResourceControllerTest {

    private static File file = new File("src/test/resources/mp3/Anton_Barbardjan-IT_My_-track_title-NRDiEByTRMFazF6O.mp3");

    @MockBean
    private SongService songService;


    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveFile() throws Exception {

        Resource fileResource = new ClassPathResource(
                "mp3/Anton_Barbardjan-IT_My_-track_title-NRDiEByTRMFazF6O.mp3");

        MockMultipartFile multipartFile =
                new MockMultipartFile("file",
                        file.getName(),
                        String.valueOf(MediaType.APPLICATION_OCTET_STREAM),
                        FileUtils.readFileToByteArray(file));

        Mockito.when(songService.saveSong(multipartFile)).thenReturn(1);

        mockMvc.perform(multipart("/resources")
                        .file(multipartFile))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(1, songService.saveSong(multipartFile)));
    }

    @Test
    void get() throws Exception {
        Integer id = 1;

        Mockito.when(songService.getSongById(id)).thenReturn(new byte[1]);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/resources/?id=1"))
                .andExpect(status().isOk());

    }

    @Test
    void delete() {
    }

    @Test
    void getMetadata() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/resources/?id=1"))
                .andExpect(status().isOk());

    }
}