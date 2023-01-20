package ec.backend.reactive.talentzone.playlist.usecases.interfaces;

import ec.backend.reactive.talentzone.playlist.dto.PlaylistDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface UpdatePlaylist {
    Mono<PlaylistDTO> applyUseCase(String playlistId, PlaylistDTO playlistDTO);
}
