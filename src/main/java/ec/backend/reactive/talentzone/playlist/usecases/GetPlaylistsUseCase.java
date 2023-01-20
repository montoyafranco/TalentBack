package ec.backend.reactive.talentzone.playlist.usecases;

import ec.backend.reactive.talentzone.playlist.repository.IPlaylistRepository;
import ec.backend.reactive.talentzone.playlist.usecases.interfaces.GetPlaylists;
import ec.backend.reactive.talentzone.playlist.dto.PlaylistDTO;
import ec.backend.reactive.talentzone.playlist.mapper.PlaylistMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetPlaylistsUseCase implements GetPlaylists {
    private final IPlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;

    @Override
    public Flux<PlaylistDTO> applyUseCase() {
        return this.playlistRepository
                .findAll()
                .switchIfEmpty(Flux.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                .map(playlist -> playlistMapper.convertEntityToDTO().apply(playlist));
    }
}
