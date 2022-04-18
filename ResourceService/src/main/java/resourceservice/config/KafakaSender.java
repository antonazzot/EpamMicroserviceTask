package resourceservice.config;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import resourceservice.model.SongDTO;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafakaSender {

    private final KafkaTemplate <String, SongDTO> kafkaSongTemplate;

      public void sendMessageWithCallback(SongDTO songDTO) throws ExecutionException, InterruptedException, TimeoutException {

          ListenableFuture<SendResult<String, SongDTO>> future =
                    kafkaSongTemplate.send("uploadsong", songDTO.getId().toString(), songDTO);


          future.addCallback(new ListenableFutureCallback<SendResult<String, SongDTO>>() {

              @Override
              public void onSuccess(SendResult<String, SongDTO> result) {
                  log.info("Success send songDTo={}", result.getProducerRecord().key() + " " + result.getProducerRecord().value());
              }

              @Override
              public void onFailure(Throwable ex) {
                  log.error("Eror!"+ "  " +ex.getMessage());
              }

          });
        future.completable().get(10, TimeUnit.MILLISECONDS);
    }
}

