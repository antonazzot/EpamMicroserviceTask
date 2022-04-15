package songservice.kafkametaconfig;

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
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaMetaConsumer {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapservers;

    public Map<String, Object> consumerConfig() {
        HashMap<String, Object> prop = new HashMap<>();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapservers);
//        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        prop.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

//        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "mygroup2");
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
    public ConsumerFactory<String, List<Integer>> consumerListFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new JsonDeserializer<>(List.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<Integer>> kafkaListJsonListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, List<Integer>> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerListFactory());
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }


}
