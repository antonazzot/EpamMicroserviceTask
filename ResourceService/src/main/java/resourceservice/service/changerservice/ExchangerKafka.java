package resourceservice.service.changerservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import resourceservice.model.SongDTO;
@Service
@RequiredArgsConstructor
public class ExchangerKafka implements Exchanger {
    private final KafkaService kafkaService;
    private final DeleteInterfece deleteByKafka;
    @Override
    public SongDTO receiveSong(SongDTO songDTO) {
       return kafkaService.sendWithSongDtoReply(songDTO);
    }

    @Override
    public void deleteSongById(Integer[] id) {
        deleteByKafka.deleteFromMetadata(id);
    }

    @Override
    public String getMetaById(Integer id) {
        return kafkaService.getMetaById(id);
    }
}
