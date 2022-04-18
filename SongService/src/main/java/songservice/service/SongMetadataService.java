package songservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import resourceprocessor.dto.MetadataDTO;
import resourceservice.model.SongDTO;
import songservice.model.SongMetadata;
import songservice.repository.SongMetadataRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongMetadataService {

    private final SongMetadataRepository songMetadataRepository;
    private final MetaDataConvertor metaDataConvertorJson;
    private final UserMetadataConvectorJson userMetadataConvectorJsonImp;

    public Integer saveSongMetadata(MetadataDTO metadataDTO) {
        SongMetadata songMetadata =  SongMetadata.builder()
                .id(metadataDTO.getSongId())
                .metadata(metaDataConvertorJson.getMetadata(metadataDTO.getObjectMetadata())).build();
       return songMetadataRepository.save(songMetadata).getId();
    }


    public void deleteById(List <Integer>  id) {
//        Iterable<SongMetadata> allById = songMetadataRepository.findAllById(Arrays.asList(id));
//        List <Integer> integerList = new ArrayList<>();
//        for (SongMetadata songMetadata : allById) {
//            integerList.add(songMetadata.getId());
//        }
        songMetadataRepository.
                deleteAllById(id);
    }

    public Optional <String> getSongsById(Integer id) {
        return Optional.of(songMetadataRepository.findById(id).orElseThrow().getMetadata());
    }

    public Optional<SongMetadata> getSongMetaById (Integer id) {
        return songMetadataRepository.findById(id);
    }

    public void saveSongMetadataFromKafka(SongDTO songDTO) {
        SongMetadata songMetadata =  SongMetadata.builder()
                .id(songDTO.getId())
                .metadata(userMetadataConvectorJsonImp.getJsonFromUserMetadata(songDTO.getUserMetadata())).build();
         songMetadataRepository.save(songMetadata);
    }
}
