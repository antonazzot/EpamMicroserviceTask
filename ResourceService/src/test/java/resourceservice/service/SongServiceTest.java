package resourceservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import resourceservice.model.Song;
import resourceservice.repository.SongRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SongServiceTest {
    @MockBean
    private  SongRepository songRepository;
    @MockBean
    private  AmazonService amazonService;
    @MockBean
    private  FileValidator fileValidatorService;
    @MockBean
    private  KafkaService kafkaService;

    @Test
    void getSongById() {
        Integer songId = 1;
        Song song = new Song(1, "somename", 1L, "mybucket");
        Mockito.when(songRepository.findById(1)).thenReturn(Optional.of(new Song(1, "somename", 1L, "mybucket")));

        assertNotNull(songRepository.findById(songId));
        assertEquals(song, songRepository.findById(songId).get());
        assertEquals("somename", songRepository.findById(songId).get().getSongName());
        assertEquals(1L, songRepository.findById(songId).get().getSongSize());
        assertEquals("mybucket", songRepository.findById(songId).get().getSongAWSBucketName());
    }

    @Test
    void saveSong() throws IOException {
        Resource fileResource = new ClassPathResource(
                "mp3/Anton_Barbardjan-IT_My_-track_title-NRDiEByTRMFazF6O.mp3");

        MockMultipartFile firstFile = new MockMultipartFile(
                "attachments",fileResource.getFilename(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                fileResource.getInputStream());

        assertNotNull(firstFile);

        Mockito.when(fileValidatorService.validateFile(firstFile)).thenReturn(true);
        Assertions.assertTrue(fileValidatorService.validateFile(firstFile));

        Song song = Song.builder()
                .id(1)
                .songName(firstFile.getName())
                .songSize(firstFile.getSize())
                .songAWSBucketName("bucketName")
                .build();
        Mockito.when(songRepository.save(song)).thenReturn(song);

        Assertions.assertEquals(1, songRepository.save(song).getId());
        Assertions.assertEquals(firstFile.getSize() ,songRepository.save(song).getSongSize());
        Assertions.assertEquals(firstFile.getName() ,songRepository.save(song).getSongName());
        Assertions.assertEquals("bucketName", songRepository.save(song).getSongAWSBucketName());
    }

    @Test
    void deleteSongById() {
        Integer id = 1;
        Song song = Song.builder()
                .id(1)
                .songName("file")
                .songSize(30L)
                .songAWSBucketName("bucketName")
                .build();

        Mockito.when(songRepository.findById(1)).thenReturn(Optional.of(song));


        Assertions.assertEquals(id, songRepository.findById(1).get().getId());
        Assertions.assertEquals("file", songRepository.findById(1).get().getSongName());


    }
}