package resourceprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration
@EnableKafka
public class ResourceProcessor {
    public static void main(String[] args) {
        SpringApplication.run(ResourceProcessor.class, args);
    }
}
