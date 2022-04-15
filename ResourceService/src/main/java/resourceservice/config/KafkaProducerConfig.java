package resourceservice.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import resourceservice.model.SongDTO;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapservers;

    public Map<String, Object> producerConfig() {
        HashMap<String, Object> prop = new HashMap<>();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapservers);
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return prop;
    }

    @Bean
    KafkaTemplate<String, SongDTO> kafkaTemplate() {
        KafkaTemplate<String, SongDTO> kafkaTemplate = new KafkaTemplate<>(producerObjectFactory());
        kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
        kafkaTemplate.setDefaultTopic("uploadsong");
        kafkaTemplate.setProducerListener(new ProducerListener<String, SongDTO>() {
            @Override
            public void onSuccess(ProducerRecord<String, SongDTO> producerRecord, RecordMetadata recordMetadata) {
                log.info("ACK from ProducerListener message: {} offset:  {}", producerRecord.value(),
                        recordMetadata.offset());
            }
        });
        return kafkaTemplate;
    }

    @Bean
    public ProducerFactory<String, SongDTO> producerObjectFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, SongDTO> kafkaSongTemplate(@Autowired ProducerFactory<String, SongDTO> producerObjectFactory) {
        return new KafkaTemplate<>(producerObjectFactory);
    }

}
