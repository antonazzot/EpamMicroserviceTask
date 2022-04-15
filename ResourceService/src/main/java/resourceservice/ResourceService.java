package resourceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaClient
@EnableKafka
public class ResourceService {
    public static void main(String[] args) {
        SpringApplication.run(ResourceService.class, args);
    }
}
