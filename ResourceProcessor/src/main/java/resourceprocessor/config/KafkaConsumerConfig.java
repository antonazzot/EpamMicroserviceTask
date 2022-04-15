package resourceprocessor.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
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
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "mygroup1");
        return prop;
    }

    @Bean
    public ConsumerFactory<String, SongDTO> consumerObjectFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new JsonDeserializer<>(String.class), new JsonDeserializer<>(SongDTO.class));
    }


    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, SongDTO>> kafkaListenerContainerFactory (ConsumerFactory<String, SongDTO>  consumerObjectFactory) {

        ConcurrentKafkaListenerContainerFactory<String, SongDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerObjectFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaJsonListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory(ConsumerFactory<String, String>  consumerObjectFactory) {
//        ConcurrentKafkaListenerContainerFactory<String, String> listener = new ConcurrentKafkaListenerContainerFactory<>();
//        listener.setConsumerFactory(consumerObjectFactory);
//        return listener;
//    }
}
