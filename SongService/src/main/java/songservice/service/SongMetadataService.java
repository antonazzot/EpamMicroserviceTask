package songservice.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import resourceprocessor.dto.MetadataDTO;
import songservice.model.SongMetadata;
import songservice.repository.SongMetadataRepository;

import java.util.Arrays;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SongMetadataService {

    private final SongMetadataRepository songMetadataRepository;

    public Integer saveSong (MetadataDTO metadataDTO) {
        SongMetadata songMetadata =  SongMetadata.builder()
                .id(metadataDTO.getSongId())
                .metadata(extractStringMetadata(metadataDTO.getObjectMetadata())).build();
       return songMetadataRepository.save(songMetadata).getId();
    }
    private String extractStringMetadata (ObjectMetadata objectMetadata) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> userMetadata = objectMetadata.getUserMetadata();
        userMetadata.values().forEach(s-> stringBuilder.append(s+" : "+userMetadata.get(s)+", "));
        return stringBuilder.toString();
    }

    public void deleteById(Integer[] id) {
        songMetadataRepository.deleteAllById(Arrays.stream(id).toList());
    }
}
