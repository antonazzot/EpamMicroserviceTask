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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SongService {
    @Value("${aws.s3.bucketname}")
    private String bucketName;

    private final SongRepository songRepository;
    private final AmazonService amazonService;
    private final FileValidator fileValidatorService;
    private final KafkaService kafkaService;

    public ResponseEntity<?> saveSong (MultipartFile file) {

        if (fileValidatorService.validateFile(file))
            return ResponseEntity.badRequest().body(new IllegalArgumentException("File mustn't be empty or file format not supported"));
        Song song = Song.builder()
                .songName(file.getName())
                .songSize(file.getSize())
                .songAWSBucketName(bucketName)
                .build();
        Integer songId = songRepository.save(song).getId();
        amazonService.extractMetaAndPutS3(songId, file, bucketName);
        return ResponseEntity.ok(songId);
    }

    public ResponseEntity <?> getSongById (Integer id) {
        if (!songRepository.existsById(id))
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).header("Id problem", "Id not found").build();
        Song song = songRepository.findById(id).orElseThrow();
        return ResponseEntity.of(Optional.of(amazonService.getSongById(id, bucketName)));
    }

    public ResponseEntity<?> deleteSongById(Integer[] id) {
        List<Song> songs = songRepository.findAllById(Arrays.asList(id));
        songRepository.deleteAllById(songs.stream().map(Song::getId).collect(Collectors.toList()));
        amazonService.deleteSongs(songs);
        kafkaService.deleteFromMetadata(id);
        int[] result =  songs.stream().mapToInt(Song::getId).toArray();
        return ResponseEntity.of(Optional.of(result));
    }

    public ResponseEntity<?> getMetaById(Integer id) {
        return ResponseEntity.of(Optional.of(kafkaService.getMetaById(id)));
    }
}
