package resourceservice.service.changerservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeleteService implements DeleteInterfece {

    private  final RestTemplate restTemplate;
    private final String DELETE_PARAM = "deleteid";

    @Override
    public void deleteFromMetadata(Integer[] deleteId) {
        Map<String, Integer> deleteMap = new HashMap<>();
        for (Integer id : deleteId) {
            deleteMap.put(DELETE_PARAM, id);
        }
        restTemplate.delete("http://METADATA/metadata/delete/"+"{"+DELETE_PARAM+"}",  deleteMap);
    }
}
