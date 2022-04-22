package resourceservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;
import resourceservice.model.SongDTO;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
@Slf4j
@Service
public class KafakaSender {

    private final ReplyingKafkaTemplate<String, String, SongDTO> replyingDtoTemplate;

      public SongDTO sendMessageWithCallback(SongDTO songDTO) throws ExecutionException, InterruptedException, TimeoutException, JsonProcessingException {

          ObjectMapper objectMapper = new ObjectMapper();
          String s = objectMapper.writeValueAsString(songDTO);

          ProducerRecord <String, String> record = new ProducerRecord<>("uploadsong", s);
          RequestReplyFuture<String, String, SongDTO> future = replyingDtoTemplate.sendAndReceive(record);

          future.addCallback(new ListenableFutureCallback<ConsumerRecord<String, SongDTO>>() {
              @Override
              public void onFailure(Throwable ex) {
                  System.out.println("**********" + "failed" +"*************");
                  log.error("Error in replying ={}", ex.getMessage());
                  throw new RuntimeException(ex.getMessage());
              }
              @Override
              public void onSuccess(ConsumerRecord<String, SongDTO> result) {
                  System.out.println("**********" + "success" +"*************");
              }
          });
            return future.get(1000, TimeUnit.MILLISECONDS).value();

    }
}

