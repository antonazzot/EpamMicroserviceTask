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
import org.springframework.util.concurrent.ListenableFuture;
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
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
@Service
public class AmazonService {
    private final AmazonS3 amazonS3;
    private final MetadataExtractor metadataExtractor;
    private final KafkaTemplate<String, SongDTO> kafkaSongTemplate;
    private final KafakaSender kafakaSender;

    @Async
    @SneakyThrows
    public void extractMetaAndPutS3 (Integer songId, MultipartFile multipartFile, String bucketName) {
        SongDTO songDTO = SongDTO.builder()
                .id(songId)
                .file(convertMultiPartToFile(multipartFile))
                .userMetadata(new HashMap<>())
                .build();

        try {
            kafakaSender.sendMessageWithCallback(songDTO);
        }
        catch (Throwable ex) {
            log.error(ex.getMessage());
        }


        amazonS3.createBucket(bucketName);
        amazonS3.putObject(bucketName, songId.toString(), multipartFile.getInputStream(), extractObjectMetadata(multipartFile));
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

    private ObjectMetadata extractObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.getUserMetadata().put("fileExtension", file.getOriginalFilename());
        return objectMetadata;
    }
    @Async
    public void deleteSongs(List<Song> songs) {
        songs.forEach(song -> amazonS3.deleteObject(song.getSongAWSBucketName(), song.getId().toString()));

    }
}
