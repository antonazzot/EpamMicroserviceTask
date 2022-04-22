package resourceservice.service.changerservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import resourceservice.model.SongDTO;
@Service
@RequiredArgsConstructor
public class ExchangerEvreka implements Exchanger {
    private final MetadataExtractorInterfece metadataExtractor;
    private final DeleteInterfece deleteService;

    @Override
    public SongDTO receiveSong(SongDTO songDTO) {
        return metadataExtractor.extractMetadata(songDTO);
    }

    @Override
    public void deleteSongById(Integer[] id) {
        deleteService.deleteFromMetadata(id);
    }

    @Override
    public String getMetaById(Integer id) {
        return metadataExtractor.getMetadataFromBD(id);
    }
}
