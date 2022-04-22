package resourceservice.service.changerservice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import resourceservice.model.SongDTO;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChangerSender {
    @Value("${changeBy}")
    String changeBy;

    private final Exchanger exchangerEvreka;
    private final Exchanger exchangerKafka;


    private Map<String, Exchanger> exchangerMap;

    @PostConstruct
    private void setUpExchange () {
        this.exchangerMap = Map.of("evreka", exchangerEvreka, "kafka" ,exchangerKafka);
    }

    public SongDTO sendAndRecive (SongDTO songDTO) {
        return exchangerMap.get(changeBy).receiveSong(songDTO);
    }

    public void deleteFromMeta (Integer [] id) {
         exchangerMap.get(changeBy).deleteSongById(id);
    }

    public String getMetaById (Integer id) {
        return exchangerMap.get(changeBy).getMetaById(id);
    }

}
