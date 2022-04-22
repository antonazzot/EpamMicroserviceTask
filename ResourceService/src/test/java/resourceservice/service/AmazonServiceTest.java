package resourceservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import resourceservice.model.Song;
import resourceservice.model.SongDTO;
import resourceservice.service.changerservice.KafkaService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AmazonServiceTest {
    private static File file = new File("src/test/resources/mp3/Anton_Barbardjan-IT_My_-track_title-NRDiEByTRMFazF6O.mp3");
    @Autowired
    private AmazonS3 amazonS3;
    @MockBean
    private KafkaService kafkaService;
    @Autowired
    private ObjectMetadataExtractor objectMetadataExtractor;

    @Test
    void extractMetaAndPutS3() throws IOException {

        MockMultipartFile multipartFile =
                new MockMultipartFile("file",
                        file.getName(),
                        String.valueOf(MediaType.APPLICATION_OCTET_STREAM),
                        FileUtils.readFileToByteArray(file));

        SongDTO songDTO = SongDTO.builder()
                .id(1)
                .file(file)
                .userMetadata(new HashMap<>())
                .build();

        Map<String, String> mapWithMeta = Map.of("metaname", "metavalue");

        SongDTO songDTOWithMeta = SongDTO.builder()
                .id(1)
                .file(file)
                .userMetadata(mapWithMeta)
                .build();

        Mockito.when(kafkaService.sendWithSongDtoReply(songDTO)).thenReturn(songDTOWithMeta);

        ObjectMetadata objectMetadata = objectMetadataExtractor.extractObjectMetadata(multipartFile, songDTOWithMeta.getUserMetadata());

        Assertions.assertEquals(mapWithMeta, objectMetadata.getUserMetadata());
        Assertions.assertEquals("testbucket",amazonS3.createBucket("testbucket").getName());
        Assertions.assertNotNull(amazonS3.putObject("testbucket", songDTOWithMeta.getId().toString(), multipartFile.getInputStream(), objectMetadata));

    }

    @Test
    void getSongById() throws IOException {
        Integer id  =1;
        String BUCKETNAME = "bucketname";
        S3Object s3Object = new S3Object();
        s3Object.setBucketName(BUCKETNAME);
        s3Object.setKey(id.toString());
        byte[] bytes = FileUtils.readFileToByteArray(file);
        s3Object.setObjectContent(FileUtils.openInputStream(file));
        amazonS3.createBucket(BUCKETNAME);
        amazonS3.putObject(BUCKETNAME, id.toString(), file);

       Assertions.assertEquals(id.toString(), amazonS3.getObject(BUCKETNAME, id.toString()).getKey());
       Assertions.assertEquals(BUCKETNAME, amazonS3.getObject(BUCKETNAME, id.toString()).getBucketName());
//       Assertions.assertEquals(bytes.length,  amazonS3.getObject(BUCKETNAME, id.toString()).getObjectContent().readAllBytes().length);

    }

    @Test
    void deleteSongs() {
        String BUCKETNAME = "bucketname";
        Song song = Song.builder()
                .songName(file.getName())
                .songSize(1L)
                .songAWSBucketName(BUCKETNAME)
                .build();

        List<Song> songList = List.of(new Song( ), song);

        Assertions.assertNotNull(songList);
    }
}