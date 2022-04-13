package resourceservice.repository;

import org.springframework.data.repository.CrudRepository;
import resourceservice.model.Song;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Integer> {
    @Override
    List<Song> findAllById(Iterable<Integer> integers);
}
