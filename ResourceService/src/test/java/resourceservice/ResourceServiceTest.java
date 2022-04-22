package resourceservice;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import resourceservice.controller.ResourceController;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
classes = ResourceService.class)
@DirtiesContext
@AutoConfigureMessageVerifier
public class ResourceServiceTest {

    @Autowired
    private ResourceController resourceController;

    @Before
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder
                = MockMvcBuilders.standaloneSetup(resourceController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }


}