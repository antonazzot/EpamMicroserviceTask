package resourceservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Service;
import resourceservice.config.KafakaSender;
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

    public String getMetaById (Integer id) throws IllegalArgumentException {
        ConsumerRecord<String, String> getmeta = null;
        try {
            getmeta = replyingTemplate.sendAndReceive(new ProducerRecord<String, Integer>("getmeta", id))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (getmeta!=null || !getmeta.value().isEmpty())
       return getmeta.value();
        else throw new  IllegalArgumentException ("Metadata not found");
    }


    public void deleteFromMetadata(Integer[] id) {
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
