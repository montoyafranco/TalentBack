package ec.backend.reactive.talentzone.playlist.usecases.interfaces;

import ec.backend.reactive.talentzone.song.dto.SongDTO;
import ec.backend.reactive.talentzone.playlist.dto.PlaylistDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface AddSong {
    Mono<PlaylistDTO> addToPlaylist(String playlistId, SongDTO songDTO);
}
