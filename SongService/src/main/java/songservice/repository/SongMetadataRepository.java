package songservice.repository;

import org.springframework.data.repository.CrudRepository;
import songservice.model.SongMetadata;

public interface SongMetadataRepository extends CrudRepository<SongMetadata, Integer> {
}
