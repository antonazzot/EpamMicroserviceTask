package resourceprocessor.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import resourceprocessor.processorservice.ProcessorService;
import resourceservice.model.SongDTO;
@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaListener {
    private final ProcessorService processorService;

    @org.springframework.kafka.annotation.KafkaListener(topics = "uploadsong",
                groupId = "mygroup1",
    containerFactory = "kafkaJsonListenerContainerFactory")
    public SongDTO listener (SongDTO songDTO) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        SongDTO songDTO = objectMapper.readValue(message, SongDTO.class);
//        log.info("SONGDTO ={}", songDTO.getId());

        SongDTO songDTO1 = processorService.extractMetadataAndSave(songDTO);
        return songDTO1;
    }
}

