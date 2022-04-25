package resourceservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import resourceservice.model.Song;
import resourceservice.repository.SongRepository;
import resourceservice.service.AmazonService;
import resourceservice.service.FileValidatorService;
import resourceservice.service.changerservice.ChangerSender;
import resourceservice.service.changerservice.DeleteService;
import resourceservice.service.changerservice.KafkaService;
import resourceservice.service.SongService;
import org.apache.commons.io.FileUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureMockMvc
class ResourceControllerTest {

    private static File file = new File("src/test/resources/mp3/Anton_Barbardjan-IT_My_-track_title-NRDiEByTRMFazF6O.mp3");
    private static File file2 = new File("src/test/resources/mp3/Снимок экрана 2022-04-08 в 11.29.46 AM.png");

    @Autowired
    private SongService songService;

    @Autowired
    private SongRepository songRepository;

    @MockBean
    private FileValidatorService fileValidatorService;

    @MockBean
    private AmazonService amazonService;

    @MockBean
    private KafkaService kafkaService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteService deleteService;

    @MockBean
    private ChangerSender changerSender;

    @Test
    void saveFile() throws Exception {

        String messageException = "Fail doesn't passed validate, File mustn't be empty or file format not supported";

        MockMultipartFile multipartFile =
                new MockMultipartFile("file",
                        file.getName(),
                        String.valueOf(MediaType.APPLICATION_OCTET_STREAM),
                        FileUtils.readFileToByteArray(file));

        Song song = Song.builder()
                .id(1)
                .songName(file.getName())
                .songSize(multipartFile.getSize())
                .songAWSBucketName("bucketName")
                .build();

        Mockito.when(fileValidatorService.validateFile(multipartFile)).thenReturn(false);

        mockMvc.perform(multipart("/resources")
                        .file(multipartFile))
                .andDo(print())
                .andExpect(status().isOk());

        Resource textFile = new ClassPathResource(
                "mp3/test.txt");

        MockMultipartFile multipartFile2 =
                new MockMultipartFile("file",
                        file2.getName(),
                        String.valueOf(MediaType.APPLICATION_OCTET_STREAM),
                        FileUtils.readFileToByteArray(file2));

        Mockito.when(fileValidatorService.validateFile(multipartFile2)).thenCallRealMethod();

        mockMvc.perform(multipart("/resources")
                        .file(multipartFile2))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(messageException, result.getResponse().getContentAsString()));

    }

    @Test
    void get() throws Exception {
        Integer existId = 183;

        SongRepository songRepository = Mockito.mock(SongRepository.class);

        Mockito.when(songRepository.existsById(existId)).thenReturn(true);

        Mockito.when(amazonService.getSongById(existId, "bucketName")).thenReturn(new byte[1]);

        mockMvc.perform(MockMvcRequestBuilders.get("/resources/?id="+existId))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(1, amazonService.getSongById(existId, "bucketName").length));

    }

    @Test
    void delete() throws Exception {
        Integer [] ids = new Integer [] {1, 2, 3};

        Mockito.doNothing().when(deleteService).deleteFromMetadata(ids);

        mockMvc.perform(MockMvcRequestBuilders.delete("/resources/?deleteid=1&deleteid=2&deleteid=3"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getMetadata() throws Exception {
        Integer id =1;

        Mockito.when(changerSender.getMetaById(id)).thenReturn("somemeta");

        mockMvc.perform(MockMvcRequestBuilders.get("/resources/getmeta/?id="+id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("somemeta" ,result.getResponse().getContentAsString()));
    }
}