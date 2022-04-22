package resourceservice.service.changerservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import resourceservice.model.SongDTO;

@Service
@RequiredArgsConstructor
public class MetadataExtractor implements MetadataExtractorInterfece {
    private final RestTemplate restTemplate;

    public SongDTO extractMetadata (SongDTO songDTO) {
        return restTemplate.postForObject("http://PARSER/parser/parse/{file}", songDTO,
                SongDTO.class, songDTO);
    }

    public String getMetadataFromBD (Integer id) {
        return restTemplate.getForObject("http://METADATA/metadata/songs/{id}", String.class,
                id);
    }

}
