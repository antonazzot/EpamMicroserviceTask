package resourceprocessor.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                groupId = "mygroup1")

    @SendTo("uploadmeta")
    public SongDTO listener (SongDTO songDTO) throws JsonProcessingException {

       return processorService.extractMetadataAndSave(songDTO);
    }
}

