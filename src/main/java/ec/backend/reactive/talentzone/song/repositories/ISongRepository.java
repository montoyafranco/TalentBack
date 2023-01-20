package ec.backend.reactive.talentzone.song.repositories;

import ec.backend.reactive.talentzone.song.collections.Song;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISongRepository extends ReactiveMongoRepository<Song,String> {
}
