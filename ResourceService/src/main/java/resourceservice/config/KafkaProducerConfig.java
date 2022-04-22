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
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import resourceservice.model.SongDTO;

import java.util.HashMap;
import java.util.List;
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
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return prop;
    }

//    @Bean
//    KafkaTemplate<String, SongDTO> kafkaTemplate() {
//        KafkaTemplate<String, SongDTO> kafkaTemplate = new KafkaTemplate<>(producerObject1Factory());
//        kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
//        kafkaTemplate.setDefaultTopic("uploadsong");
////        kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
////            @Override
////            public void onSuccess(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata) {
//////                log.info("ACK from ProducerListener message: {} offset:  {}", producerRecord.value(),
//////                        recordMetadata.offset());
//////            }
//////        });
//        return kafkaTemplate;
//    }
//
//    /**
//     *  SongDTO Producer */
//    @Bean
//    public ProducerFactory<String, SongDTO> producerObject1Factory() {
//        return new DefaultKafkaProducerFactory<>(producerConfig());
//    }

    @Bean
    public ProducerFactory<String, String> producerObjectFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaStrTemplate(@Autowired ProducerFactory<String, String> producerObjectFactory) {
        return new KafkaTemplate<>(producerObjectFactory);
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, String> repliesDtoContainer(
            ConcurrentKafkaListenerContainerFactory<String, String> kafkaJsonListenerContainerFactory) {

        ConcurrentMessageListenerContainer<String, String> repliesContainer =
                kafkaJsonListenerContainerFactory.createContainer("uploadmeta");
        repliesContainer.getContainerProperties().setGroupId("mygroup2");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingDtoTemplate(
            @Autowired   ProducerFactory<String, String> producerObjectFactory,
            @Autowired  ConcurrentMessageListenerContainer<String, String> repliesDtoContainer) {
        return new ReplyingKafkaTemplate<>(producerObjectFactory, repliesDtoContainer);
    }

    /**
     *  Delete by id's list Producer */

    @Bean
    public ProducerFactory<String, List<Integer>> producerListFactory() {
        return new DefaultKafkaProducerFactory<>(producerStrConfig());
    }

    @Bean
    public KafkaTemplate<String, List<Integer>> kafkaDeleteTemplate(@Autowired ProducerFactory<String, List<Integer>> producerListFactory) {
        return new KafkaTemplate<>(producerListFactory);
    }

    /**
     *  Get metadata by id's Producer */

    public Map<String, Object> producerStrConfig() {
        HashMap<String, Object> prop = new HashMap<>();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapservers);
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return prop;
    }

    @Bean
    public ProducerFactory<String, Integer> producerGetMetaFactory() {
        return new DefaultKafkaProducerFactory<>(producerStrConfig());
    }

    @Bean
    public KafkaTemplate<String, Integer> kafkaGetMetaTemplate(@Autowired ProducerFactory<String, Integer> producerGetMetaFactory) {
        return new KafkaTemplate<>(producerGetMetaFactory);
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, String> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, String> kafkaStrListenerContainerFactory) {

        ConcurrentMessageListenerContainer<String, String> repliesContainer =
                kafkaStrListenerContainerFactory.createContainer("receivemeta");
        repliesContainer.getContainerProperties().setGroupId("group9");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

    @Bean
    public ReplyingKafkaTemplate<String, Integer, String> replyingTemplate(
         @Autowired   ProducerFactory<String, Integer> producerGetMetaFactory,
          @Autowired  ConcurrentMessageListenerContainer<String, String> repliesContainer) {
        return new ReplyingKafkaTemplate<>(producerGetMetaFactory, repliesContainer);
    }


}
