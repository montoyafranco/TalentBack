package ec.backend.reactive.talentzone.playlist.repository;

import ec.backend.reactive.talentzone.playlist.collection.Playlist;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlaylistRepository extends ReactiveMongoRepository<Playlist,String> {
}