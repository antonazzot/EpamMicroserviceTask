package resourceprocessor.processorservice;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;
import resourceprocessor.dto.MetadataDTO;
import resourceservice.model.SongDTO;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ProcessorService {

    private final RestTemplate restTemplate;
    private final TikaExtractorService tikaExtractorService;

    public ObjectMetadata extractMetadataAndSave (SongDTO songDTO) {
        MetadataDTO metadataDTO = MetadataDTO.builder()
                .songId(songDTO.getId())
                .build();
        ObjectMetadata objectMetadata = null;
        try {
            objectMetadata = tikaExtractorService.objectMetadataExtractor(songDTO.getFile());
            metadataDTO.setObjectMetadata(objectMetadata);
        } catch (TikaException | IOException | SAXException e) {
            e.printStackTrace();
        }
//        restTemplate.postForObject("http://METADATA/metadata/save1/{id}", metadataDTO.getSongId(),
//                Integer.class,  metadataDTO.getSongId());

        restTemplate.postForObject("http://METADATA/metadata/save/{metadatadto}", metadataDTO,
                Integer.class,  metadataDTO);
       return objectMetadata;
    }

}
