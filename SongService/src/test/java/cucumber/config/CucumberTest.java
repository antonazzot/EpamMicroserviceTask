package cucumber.config;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
        plugin = {"pretty", "json:target/cucumber-report.json"},
        monochrome = false,
        glue = "cucumber"
)
@CucumberContextConfiguration
public class CucumberTest {

}
