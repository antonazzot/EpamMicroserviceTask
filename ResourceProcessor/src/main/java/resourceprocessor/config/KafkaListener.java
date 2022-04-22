package resourceprocessor.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import resourceprocessor.processorservice.ProcessorService;
import resourceprocessor.processorservice.SongDTO;


@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaListener {
    private final ProcessorService processorService;

    @org.springframework.kafka.annotation.KafkaListener(topics = "uploadsong",
                groupId = "mygroup1")

    @SendTo("uploadmeta")
    public String listener (String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SongDTO songDTO = objectMapper.readValue(message, SongDTO.class);

        SongDTO songDTOReturn = processorService.extractMetadataAndSave(songDTO);

        return objectMapper.writeValueAsString(songDTOReturn);
    }
}

