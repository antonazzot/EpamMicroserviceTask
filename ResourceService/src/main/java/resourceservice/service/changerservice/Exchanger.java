package resourceservice.service.changerservice;

import org.springframework.stereotype.Service;
import resourceservice.model.SongDTO;
@Service
public interface Exchanger {
    public SongDTO receiveSong (SongDTO songDTO);

    public void deleteSongById (Integer [] id);

    public String getMetaById(Integer id);
}
