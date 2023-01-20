package ec.backend.reactive.talentzone.playlist.usecases;

import ec.backend.reactive.talentzone.playlist.dto.PlaylistDTO;
import ec.backend.reactive.talentzone.playlist.mapper.PlaylistMapper;
import ec.backend.reactive.talentzone.playlist.repository.IPlaylistRepository;
import ec.backend.reactive.talentzone.playlist.usecases.interfaces.SavePlaylist;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SavePlaylistUseCase implements SavePlaylist {

    private final IPlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;

    @Override
    public Mono<PlaylistDTO> applyUseCase(PlaylistDTO playlistDTO) {
        return this.playlistRepository.save(playlistMapper.convertDTOToEntity().apply(Objects.requireNonNull(playlistDTO)))
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_ACCEPTABLE.toString())))
                .map(playlist -> playlistMapper.convertEntityToDTO().apply(playlist));
    }
}
