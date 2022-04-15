package resourceprocessor.processorservice;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.tika.exception.TikaException;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;
import resourceprocessor.config.KafkaListener;
import resourceprocessor.dto.MetadataDTO;
import resourceservice.model.SongDTO;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ProcessorService {

    private final RestTemplate restTemplate;
    private final TikaExtractorService tikaExtractorService;
//    private final KafkaTemplate <String, SongDTO> kafkaTemplate;


    public SongDTO extractMetadataAndSave (SongDTO songDTO) {

        MetadataDTO metadataDTO = MetadataDTO.builder()
                .songId(songDTO.getId())
                .build();

        ObjectMetadata objectMetadata = null;
        try {
            objectMetadata = tikaExtractorService.objectMetadataExtractor(songDTO.getFile());
            metadataDTO.setObjectMetadata(objectMetadata);
            songDTO.setUserMetadata(objectMetadata.getUserMetadata());
        } catch (TikaException | IOException | SAXException e) {
            e.printStackTrace();
        }

//        restTemplate.postForObject("http://METADATA/metadata/save/{metadatadto}", metadataDTO,
//                Integer.class,  metadataDTO);

       return songDTO;
    }

}
