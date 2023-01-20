package ec.backend.reactive.talentzone.playlist.usecases.interfaces;

import ec.backend.reactive.talentzone.playlist.dto.PlaylistDTO;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface GetPlaylists {
    Flux<PlaylistDTO> applyUseCase();
}
