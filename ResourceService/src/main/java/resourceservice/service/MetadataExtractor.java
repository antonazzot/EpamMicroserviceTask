package resourceservice.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import resourceservice.model.SongDTO;

@Service
@RequiredArgsConstructor
public class MetadataExtractor {
    private final RestTemplate restTemplate;

    public ObjectMetadata extractMetadata (SongDTO songDTO) {
        return restTemplate.postForObject("http://PARSER/parser/parse/{file}", songDTO,
                ObjectMetadata.class, songDTO);
    }
}
