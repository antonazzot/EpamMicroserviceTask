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

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
@Slf4j
@Service
public class KafakaSender {

    private final ReplyingKafkaTemplate<String, String, String> replyingDtoTemplate;

      public SongDTO sendMessageWithCallback(SongDTO songDTO) throws ExecutionException, InterruptedException, TimeoutException, JsonProcessingException {

          ObjectMapper objectMapper = new ObjectMapper();
          String s = objectMapper.writeValueAsString(songDTO);

          ProducerRecord <String, String> record = new ProducerRecord<>("uploadsong", s);
          RequestReplyFuture<String, String, String> future = replyingDtoTemplate.sendAndReceive(record);

          future.addCallback(new ListenableFutureCallback<ConsumerRecord<String, String>>() {
              @Override
              public void onFailure(Throwable ex) {
                  System.out.println("**********" + "failed" +"*************");
                  log.error("Error in replying ={}", ex.getMessage());
                  throw new RuntimeException(ex.getMessage());
              }
              @Override
              public void onSuccess(ConsumerRecord<String, String> result) {
                  System.out.println("**********" + "success" +"*************");
              }
          });
           SongDTO result = objectMapper.readValue(future.get(10000, TimeUnit.MILLISECONDS).value(), SongDTO.class);
          Map<String, String> userMetadata = result.getUserMetadata();
          return result;
    }
}

