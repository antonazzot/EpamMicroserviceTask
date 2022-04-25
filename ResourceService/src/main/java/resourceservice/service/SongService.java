package resourceservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import resourceservice.exception.MyCustomAppException;
import resourceservice.model.Song;
import resourceservice.repository.SongRepository;
import resourceservice.service.changerservice.ChangerSender;

import java.util.Arrays;
import java.util.List;
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
    private final ChangerSender changerSender;

    public Integer saveSong (MultipartFile file) {

        if (fileValidatorService.validateFile(file))
            throw new MyCustomAppException("Fail doesn't passed validate, File mustn't be empty or file format not supported");
        Song song = Song.builder()
                .songName(file.getName())
                .songSize(file.getSize())
                .songAWSBucketName(bucketName)
                .build();
        Integer songId = songRepository.save(song).getId();
        amazonService.extractMetaAndPutS3(songId, file, bucketName);
        return songId;
    }

    public byte [] getSongById (Integer id) {
        if (songRepository.existsById(id))
            return amazonService.getSongById(id, bucketName);
        else
            throw new MyCustomAppException("Song with id "  + id + " does not exist");
    }

    public int []  deleteSongById(Integer[] id) {
        List<Song> songs = songRepository.findAllById(Arrays.asList(id));
        songRepository.deleteAllById(songs.stream().map(Song::getId).collect(Collectors.toList()));
        amazonService.deleteSongs(songs);
        changerSender.deleteFromMeta(id);
        return songs.stream().mapToInt(Song::getId).toArray();
    }

    public String getMetaById(Integer id) {
        return changerSender.getMetaById(id);
    }
}
