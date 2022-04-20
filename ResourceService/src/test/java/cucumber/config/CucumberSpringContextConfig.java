package cucumber.config;


import com.ctc.wstx.io.ReaderSource;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import resourceservice.ResourceService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = ResourceService.class, loader = SpringBootContextLoader.class)
@Slf4j
@CucumberContextConfiguration
public class CucumberSpringContextConfig {

@Before
        public void setUp() {

            log.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
    }
}
