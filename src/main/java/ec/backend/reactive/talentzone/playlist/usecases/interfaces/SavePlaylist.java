package ec.backend.reactive.talentzone.playlist.usecases.interfaces;

import ec.backend.reactive.talentzone.playlist.dto.PlaylistDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface SavePlaylist {
    Mono<PlaylistDTO> applyUseCase(PlaylistDTO playlistDTO);
}
