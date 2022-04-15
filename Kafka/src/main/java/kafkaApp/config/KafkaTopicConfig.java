package kafkaApp.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic uploadSongTopic (){
        return TopicBuilder.name("uploadsong").build();
    }

    @Bean
    public NewTopic uploadMetadataTopic (){
        return TopicBuilder.name("uploadmeta").build();
    }
}
