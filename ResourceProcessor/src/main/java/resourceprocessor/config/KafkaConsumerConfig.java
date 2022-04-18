package resourceprocessor.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import resourceservice.model.SongDTO;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapservers;


    public Map<String, Object> consumerConfig() {
        HashMap<String, Object> prop = new HashMap<>();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapservers);
        prop.put(org.springframework.kafka.support.serializer.JsonDeserializer.TRUSTED_PACKAGES, "*");
        return prop;
    }

    @Bean
    public ConsumerFactory<String, SongDTO> consumerObjectFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new JsonDeserializer<>(SongDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SongDTO> kafkaJsonListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SongDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerObjectFactory());
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, SongDTO>> kafkaListenerContainerFactory(@Autowired KafkaTemplate <String, SongDTO > kafkaTemplate) {
        ConcurrentKafkaListenerContainerFactory<String, SongDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerObjectFactory());
        factory.setReplyTemplate(kafkaTemplate);
        return factory;
    }

}
