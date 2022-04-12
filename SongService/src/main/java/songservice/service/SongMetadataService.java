package songservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import resourceprocessor.dto.MetadataDTO;
import songservice.model.SongMetadata;
import songservice.repository.SongMetadataRepository;

@Service
@RequiredArgsConstructor
public class SongMetadataService {

    private final SongMetadataRepository songMetadataRepository;

    public Integer saveSong (MetadataDTO metadataDTO) {
        SongMetadata songMetadata =  SongMetadata.builder()
                .id(metadataDTO.getSongId())
                .metadata(metadataDTO.getObjectMetadata().toString()).build();
       return songMetadataRepository.save(songMetadata).getId();
    }
}
