package ec.backend.reactive.talentzone.playlist.usecases;

import ec.backend.reactive.talentzone.playlist.dto.PlaylistDTO;
import ec.backend.reactive.talentzone.playlist.mapper.PlaylistMapper;
import ec.backend.reactive.talentzone.playlist.repository.IPlaylistRepository;
import ec.backend.reactive.talentzone.playlist.usecases.interfaces.GetPlaylistById;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetPlaylistByIdUseCase implements GetPlaylistById {
    private final IPlaylistRepository playlistRepository;

    private final PlaylistMapper playlistMapper;


    @Override
    public Mono<PlaylistDTO> getPlaylist(String playlistId) {
        return this.playlistRepository
                .findById(playlistId)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(playlist -> playlistMapper.convertEntityToDTO().apply(playlist));
    }
}
