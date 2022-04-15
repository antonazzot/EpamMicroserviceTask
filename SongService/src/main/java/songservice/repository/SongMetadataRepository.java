package songservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import songservice.model.SongMetadata;

public interface SongMetadataRepository extends CrudRepository<SongMetadata, Integer> {

    @Transactional
    @Modifying
    @Query("delete from SongMetadata s where s.id = ?1")
    void deleteAllById(Iterable<? extends Integer> integers);
}
