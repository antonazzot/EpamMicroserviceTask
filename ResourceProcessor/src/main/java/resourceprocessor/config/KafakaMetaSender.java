package resourceprocessor.config;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import resourceprocessor.dto.MetadataDTO;
import resourceservice.model.SongDTO;
@RequiredArgsConstructor
@Component
public class KafakaMetaSender {

        private final KafkaTemplate<String, MetadataDTO> kafkaTemplate;

        public void sendMessageWithCallback(MetadataDTO metadataDTO) {
            ListenableFuture<SendResult<String, MetadataDTO>> future =
                    kafkaTemplate.send("uploadsong", metadataDTO);

            future.addCallback(new ListenableFutureCallback<SendResult<String, MetadataDTO>>() {
                @Override
                public void onSuccess(SendResult<String, MetadataDTO> result) {
//                    LOG.info("Message [{}] delivered with offset {}",
//                            message,
//                            result.getRecordMetadata().offset());
                }

                @Override
                public void onFailure(Throwable ex) {

                    ex.getMessage();
                }
            });
        }
    }
