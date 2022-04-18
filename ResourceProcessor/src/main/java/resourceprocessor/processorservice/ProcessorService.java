package resourceprocessor.processorservice;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;
import resourceservice.model.SongDTO;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ProcessorService {

    private final RestTemplate restTemplate;
    private final TikaExtractorService tikaExtractorService;

    public SongDTO extractMetadataAndSave (SongDTO songDTO) {

        ObjectMetadata objectMetadata = null;
        try {
            objectMetadata = tikaExtractorService.objectMetadataExtractor(songDTO.getFile());
            songDTO.setUserMetadata(objectMetadata.getUserMetadata());
        } catch (TikaException | IOException | SAXException e) {
            e.printStackTrace();
        }

       return songDTO;
    }

}
