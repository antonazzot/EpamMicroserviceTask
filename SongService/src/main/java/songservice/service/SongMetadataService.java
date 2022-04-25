package songservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import songservice.model.SongDTO;
import songservice.model.SongMetadata;
import songservice.repository.SongMetadataRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SongMetadataService {

    private final SongMetadataRepository songMetadataRepository;
    private final MetaDataConvertor metaDataConvertorJson;
    private final UserMetadataConvectorJson userMetadataConvectorJsonImp;


    public void deleteById(List <Integer>  id) {

        try {
            songMetadataRepository.
                    deleteAllById(id);
        }
        catch (EmptyResultDataAccessException e) {
            log.error("Delete entity not exist");
        }
//        Iterable<SongMetadata> allById = songMetadataRepository.findAllById(id);
//        List <Integer> integerList = new ArrayList<>();
//        for (SongMetadata songMetadata : allById) {
//            integerList.add(songMetadata.getId());
//        }
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
