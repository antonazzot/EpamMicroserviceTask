package resourceservice.config;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import resourceservice.model.SongDTO;

import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Component
public class KafakaSender {

    private final KafkaTemplate <String, SongDTO> kafkaSongTemplate;

      public SongDTO sendMessageWithCallback(SongDTO songDTO) throws ExecutionException, InterruptedException {
            ListenableFuture<SendResult<String, SongDTO>> future =
                    kafkaSongTemplate.send("uploadsong", songDTO);

            future.addCallback(new ListenableFutureCallback<SendResult<String, SongDTO>>() {
               @Override
                public void onSuccess(SendResult<String, SongDTO> result) {
//                    LOG.info("Message [{}] delivered with offset {}",
//                            message,
//                            result.getRecordMetadata().offset());
                }

                @Override
                public void onFailure(Throwable ex) {

                    ex.getMessage();
                }
            });
           return future.get().getProducerRecord().value();
        }
}

