package resourceservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
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
        prop.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "mygroup2");
        return prop;
    }

    @Bean
    public ConsumerFactory<String, String> consumerObjectFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new StringDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaJsonListenerContainerFactory(@Autowired KafkaTemplate <String, String> kafkaStrTemplate) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerObjectFactory());
        factory.setReplyTemplate(kafkaStrTemplate);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }


    /** meta consumer */

    public Map<String, Object> consumerMetaConfig() {
        HashMap<String, Object> prop = new HashMap<>();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapservers);
        prop.put(org.springframework.kafka.support.serializer.JsonDeserializer.TRUSTED_PACKAGES, "*");
        return prop;
    }


    @Bean
    public ConsumerFactory<String, String> consumerMetaFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerMetaConfig(), new StringDeserializer(), new StringDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaStrListenerContainerFactory(@Autowired KafkaTemplate <String, String> kafkaStrTemplate) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerMetaFactory());
        factory.setReplyTemplate(kafkaStrTemplate);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

}
