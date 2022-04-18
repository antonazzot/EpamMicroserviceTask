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

    @Bean
    public NewTopic deleteSongTopic (){
        return TopicBuilder.name("deletesong").build();
    }

    @Bean
    public NewTopic getMeta() {
        return TopicBuilder.name("getmeta")
                .partitions(10)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic receiveMeta() {
        return TopicBuilder.name("receivemeta")
                .partitions(10)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic receiveDto() {
        return TopicBuilder.name("receivedto")
                .partitions(10)
                .replicas(2)
                .build();
    }
}
