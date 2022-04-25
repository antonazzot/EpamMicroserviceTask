package resourceprocessor.processorservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class EvrekaService {
    private final RestTemplate restTemplate;
    private final ProcessorService processorService;
    
    public SongDTO receiveSongToMeta (SongDTO songDTO) {
        SongDTO dto = processorService.extractMetadataAndSave(songDTO);

        restTemplate.postForObject("http://METADATA/metadata/save/", dto,
                Integer.class, dto);

        return dto;
    }
}
