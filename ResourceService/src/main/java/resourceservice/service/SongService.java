package resourceservice.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import resourceservice.model.Song;
import resourceservice.repository.SongRepository;

@Service
@RequiredArgsConstructor
public class SongService {
    private final AmazonS3 amazonS3;
    private final SongRepository songRepository;

    public Integer saveSong (MultipartFile file) {
        Integer songId = songRepository.save(new Song()).getId();
return 1;
    }
}
