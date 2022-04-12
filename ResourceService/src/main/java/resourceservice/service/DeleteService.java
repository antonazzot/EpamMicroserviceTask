package resourceservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DeleteService {

    private  final RestTemplate restTemplate;

    public void deleteFromMetadata (Integer [] deleteId) {
        restTemplate.delete("http://METADATA/metadata/delete/{deleteid}",  deleteId);
    }
}
