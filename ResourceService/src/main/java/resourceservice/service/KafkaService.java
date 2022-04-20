package resourceservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Service;
import resourceservice.config.KafakaSender;
import resourceservice.exception.MyCustomAppException;
import resourceservice.model.SongDTO;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {

    private final ReplyingKafkaTemplate<String, Integer, String> replyingTemplate;
    private final DeleteInterfece deleteByKafka;
    private final KafakaSender kafakaSender;

    public String getMetaById (Integer id) throws MyCustomAppException {
        ConsumerRecord<String, String> getmeta = null;
        try {
            getmeta = replyingTemplate.sendAndReceive(new ProducerRecord<String, Integer>("getmeta", id))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
           throw new MyCustomAppException(e.getMessage());
        }
        return getmeta.value();
    }


    public void deleteFromMetadata(Integer [] id) {
        deleteByKafka.deleteFromMetadata(id);
    }

    public SongDTO sendWithSongDtoReply (SongDTO songDTO) {
        SongDTO result = null;
        try {
         result =  kafakaSender.sendMessageWithCallback(songDTO);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
           log.error(e.getMessage());
        }
        return result;
    }
}
