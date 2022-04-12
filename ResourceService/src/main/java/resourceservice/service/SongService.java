package resourceservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import resourceservice.model.Song;
import resourceservice.repository.SongRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SongService {
    @Value("${aws.s3.bucketname}")
    private String bucketName;

    private final SongRepository songRepository;
    private final AmazonService amazonService;


    public Integer saveSong (MultipartFile file) {

        Song song = Song.builder()
                .songName(file.getName())
                .songSize(file.getSize())
                .songAWSBucketName(bucketName)
                .build();

        Integer songId = songRepository.save(song).getId();
        amazonService.extractMetaAndPutS3(songId, file, bucketName);
        return songId;
    }

    public ResponseEntity <?> getSongById (Integer id) {
        if (!songRepository.existsById(id))
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).header("Id problem", "Id not found").build();
        Song song = songRepository.findById(id).orElseThrow();
        return ResponseEntity.of(Optional.of(amazonService.getSongById(id, bucketName)));
    }

    public Boolean validateFile (MultipartFile multipartFile) {
        if (multipartFile.isEmpty() || Objects.requireNonNull(multipartFile.getContentType()).isEmpty() || !multipartFile.getContentType().equals("audio/mpeg"))
        return Boolean.TRUE;
        return Boolean.FALSE;
    }

    public Integer [] deleteSongById(Integer[] id) {
        List<Song> songs = songRepository.findAllById(Arrays.asList(id));
        songRepository.deleteAllById(Arrays.asList(id));
        amazonService.deleteSongs(songs);
        return id;
    }
}
