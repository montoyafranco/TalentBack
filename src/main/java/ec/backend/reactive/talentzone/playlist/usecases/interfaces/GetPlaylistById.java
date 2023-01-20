package ec.backend.reactive.talentzone.playlist.usecases.interfaces;

import ec.backend.reactive.talentzone.playlist.dto.PlaylistDTO;
import reactor.core.publisher.Mono;

public interface GetPlaylistById {
    Mono<PlaylistDTO> getPlaylist(String playlistId);
}
