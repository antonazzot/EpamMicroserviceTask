package resourceservice.repository;

import org.springframework.data.repository.CrudRepository;
import resourceservice.model.Song;

public interface SongRepository extends CrudRepository<Song, Integer> {
}
