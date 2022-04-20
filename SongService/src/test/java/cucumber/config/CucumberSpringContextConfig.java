package cucumber.config;


import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import resourceservice.service.SongService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@ContextConfiguration(classes = SongService.class, loader = SpringBootContextLoader.class)
@Slf4j

public class CucumberSpringContextConfig {

@Before
        public void setUp() {

            log.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
    }
}
