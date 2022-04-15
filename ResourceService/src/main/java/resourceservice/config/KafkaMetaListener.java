//package resourceservice.config;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import resourceservice.model.SongDTO;
//import resourceservice.service.AmazonService;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class KafkaMetaListener {
//    private final     AmazonService amazonService;
//
//    @org.springframework.kafka.annotation.KafkaListener(topics = "uploadmeta",
//            groupId = "mygroup2")
//    public void listener (String message) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        SongDTO songDTO = objectMapper.readValue(message, SongDTO.class);
//
//        log.info("1!!!!!!song!!!!!", songDTO.getId());
//    }
//}
