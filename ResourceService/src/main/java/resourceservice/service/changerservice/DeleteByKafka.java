package resourceservice.service.changerservice;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteByKafka implements DeleteInterfece {

    private final KafkaTemplate <String, List<Integer>> kafkaListTemplate;

    @Override
    public void deleteFromMetadata(Integer [] deleteId) {
            List<Integer> integerList = Arrays.asList(deleteId);
            kafkaListTemplate.send("deletesong", integerList);
    }
}
