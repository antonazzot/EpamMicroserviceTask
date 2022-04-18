package songservice.kafkametaconfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import resourceservice.model.SongDTO;
import songservice.service.SongMetadataService;

import java.util.List;

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

    @org.springframework.kafka.annotation.KafkaListener(topics = "deletesong",
            groupId = "mygroup3")
    public void factoryList (String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List list = objectMapper.readValue(message, List.class);
        list.forEach(System.out::println);
        songMetadataService.deleteById(list);
    }

    @org.springframework.kafka.annotation.KafkaListener(topics = "getmeta",
            groupId = "mygroup4")
    @SendTo("receivemeta")
    public String metaFactory (String sid) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Integer id = objectMapper.readValue(sid, Integer.class);
       return songMetadataService.getSongMetaById(id).orElseThrow().getMetadata();
    }
}
