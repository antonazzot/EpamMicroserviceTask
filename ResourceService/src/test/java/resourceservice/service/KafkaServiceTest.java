package resourceservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import resourceservice.config.KafakaSender;
import resourceservice.model.SongDTO;
import resourceservice.service.changerservice.DeleteInterfece;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9098", "port=9098" })
class KafkaServiceTest {

    private static final File file2 = new File("src/test/resources/mp3/Снимок экрана 2022-04-08 в 11.29.46 AM.png");
    @Autowired
    private ReplyingKafkaTemplate<String, Integer, String> replyingTemplate;
    @Autowired
    private DeleteInterfece deleteByKafka;
    @Autowired
    private KafakaSender kafakaSender;

    @MockBean
    private  ReplyingKafkaTemplate<String, SongDTO, SongDTO> replyingDtoTemplate;

    @Test
    void getMetaById() throws ExecutionException, InterruptedException {

    }

    @Test
    void deleteFromMetadata() {
    }

    @Test
    void sendWithSongDtoReply() throws ExecutionException, InterruptedException, TimeoutException {

    }
}

