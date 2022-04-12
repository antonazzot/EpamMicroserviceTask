package resourceservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import resourceservice.model.SongDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class AmazonSaverService {
    private final AmazonS3 amazonS3;
    private final MetadataExtractor metadataExtractor;


    @SneakyThrows
    public void extractMetaAndPutS3 (Integer songId, MultipartFile multipartFile, String bucketName) {
        SongDTO songDTO = SongDTO.builder()
                .id(songId)
                .file(convertMultiPartToFile(multipartFile))
                .build();

        ObjectMetadata objectMetadata = metadataExtractor.extractMetadata(songDTO);
        objectMetadata.
        log.info("metada={}", objectMetadata.getUserMetadata().size());
        amazonS3.createBucket(bucketName);
        amazonS3.putObject(bucketName, songId.toString(), multipartFile.getInputStream(), objectMetadata);
    }


    private File convertMultiPartToFile(MultipartFile file ) throws IOException
    {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }
}
