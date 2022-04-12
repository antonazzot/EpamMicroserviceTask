package resourceservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import resourceservice.model.Song;
import resourceservice.repository.SongRepository;

import java.io.File;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class SongService {
    @Value("${aws.s3.bucketname}")
    private String bucketName;

    private final SongRepository songRepository;
    private final AmazonSaverService amazonSaverService;


    public Integer saveSong (MultipartFile file) {

        Song song = Song.builder()
                .songName(file.getName())
                .songSize(file.getSize())
                .songAWSBucketName(bucketName)
                .build();

        Integer songId = songRepository.save(song).getId();
        amazonSaverService.extractMetaAndPutS3(songId, file, bucketName);
        return songId;
    }

    public Boolean validateFile (MultipartFile multipartFile) {
        if (multipartFile.isEmpty() || Objects.requireNonNull(multipartFile.getContentType()).isEmpty() || !multipartFile.getContentType().equals("audio/mpeg"))
        return Boolean.TRUE;
        return Boolean.FALSE;
    }
}
