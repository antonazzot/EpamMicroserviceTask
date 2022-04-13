package songservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import resourceprocessor.dto.MetadataDTO;
import songservice.model.SongMetadata;
import songservice.repository.SongMetadataRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongMetadataService {

    private final SongMetadataRepository songMetadataRepository;
    private final MetaDataConvertor metaDataConvertorJson;

    public Integer saveSong (MetadataDTO metadataDTO) {
        SongMetadata songMetadata =  SongMetadata.builder()
                .id(metadataDTO.getSongId())
                .metadata(metaDataConvertorJson.getMetadata(metadataDTO.getObjectMetadata())).build();
       return songMetadataRepository.save(songMetadata).getId();
    }


    public void deleteById(Integer [] id) {
        Iterable<SongMetadata> allById = songMetadataRepository.findAllById(Arrays.asList(id));
        List <Integer> integerList = new ArrayList<>();
        for (SongMetadata songMetadata : allById) {
            integerList.add(songMetadata.getId());
        }
        songMetadataRepository.deleteAllById(integerList);
    }

    public Optional <String> getSongsById(Integer id) {
        return Optional.of(songMetadataRepository.findById(id).orElseThrow().getMetadata());
    }
}
