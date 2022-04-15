package resourceservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import resourceservice.config.KafakaSender;
import resourceservice.model.Song;
import resourceservice.model.SongDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
@Service
public class AmazonService {
    private final AmazonS3 amazonS3;
    private final MetadataExtractor metadataExtractor;
    private final KafkaTemplate<String, SongDTO> kafkaTemplate;
    private final KafakaSender kafakaSender;

    @Async
    @SneakyThrows
    public void extractMetaAndPutS3 (Integer songId, MultipartFile multipartFile, String bucketName) {
        SongDTO songDTO = SongDTO.builder()
                .id(songId)
                .file(convertMultiPartToFile(multipartFile))
                .userMetadata(new HashMap<>())
//               .objectMetadata(new ObjectMetadata())
                .build();

//        kafkaTemplate.send("uploadsong", songDTO);

//        SongDTO songDtoWithUserMeta = kafakaSender.sendMessageWithCallback(songDTO);

        CompletableFuture<SendResult<String, SongDTO>> uploadsong = kafkaTemplate.send("uploadsong", songDTO).completable();
        SongDTO songDtoWithUserMeta = uploadsong.get().getProducerRecord().value();

//        kafkaSongTemplate.send("uploadmeta", songDtoWithUserMeta);
        ObjectMetadata objectMetadata = extractObjectMetadata(multipartFile, songDtoWithUserMeta.getUserMetadata());



//        ObjectMetadata objectMetadata = metadataExtractor.extractMetadata(songDTO);

        log.info("metada={}", objectMetadata.getUserMetadata().size());
        amazonS3.createBucket(bucketName);
        amazonS3.putObject(bucketName, songId.toString(), multipartFile.getInputStream(), objectMetadata);
    }

    @Async
    public byte [] getSongById(Integer id, String bucketName) {
        S3Object object = amazonS3.getObject(bucketName, id.toString());
        byte[] audiobyte = null;
        try {
           audiobyte = object.getObjectContent().readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return audiobyte;
    }


    private File convertMultiPartToFile(MultipartFile file ) throws IOException
    {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }

    private ObjectMetadata extractObjectMetadata(MultipartFile file, Map <String, String> usermetadata) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.getUserMetadata().put("fileExtension", file.getOriginalFilename());
        objectMetadata.setUserMetadata(usermetadata);
        return objectMetadata;
    }
    @Async
    public void deleteSongs(List<Song> songs) {
        songs.forEach(song -> amazonS3.deleteObject(song.getSongAWSBucketName(), song.getId().toString()));

    }
}
