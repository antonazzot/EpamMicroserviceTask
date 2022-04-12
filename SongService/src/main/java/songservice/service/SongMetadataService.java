package songservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import songservice.model.SongMetadata;
import songservice.repository.SongMetadataRepository;

@Service
@RequiredArgsConstructor
public class SongMetadataService {

    private final SongMetadataRepository songMetadataRepository;

    public Integer saveSong (Integer songId, String metaData) {
       return songMetadataRepository.save(SongMetadata.builder()
                       .id(songId)
                       .metadata(metaData)
               .build()).getId();
    }
}
