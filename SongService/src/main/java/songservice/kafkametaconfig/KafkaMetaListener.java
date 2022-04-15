package songservice.kafkametaconfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import resourceservice.model.SongDTO;
import songservice.service.SongMetadataService;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMetaListener {
    private final SongMetadataService songMetadataService;

    @org.springframework.kafka.annotation.KafkaListener(topics = "uploadmeta",
            groupId = "mygroup2")
    public void factory (String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SongDTO songDTO = objectMapper.readValue(message, SongDTO.class);
        log.info("SONGDTO ={}", songDTO.getId());

           songMetadataService.saveSongMetadataFromKafka(songDTO);
    }
}