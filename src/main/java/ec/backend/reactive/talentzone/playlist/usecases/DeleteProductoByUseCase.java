package ec.backend.reactive.talentzone.playlist.usecases;

import ec.backend.reactive.talentzone.playlist.repository.IPlaylistRepository;
import ec.backend.reactive.talentzone.playlist.usecases.interfaces.DeletePlaylist;
import ec.backend.reactive.talentzone.playlist.usecases.interfaces.GetPlaylistById;
import ec.backend.reactive.talentzone.playlist.mapper.PlaylistMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeleteProductoByUseCase implements DeletePlaylist {
    private final GetPlaylistById getPlaylistById;

    private final IPlaylistRepository playlistRepository;

    private final PlaylistMapper playlistMapper;

    @Override
    public Mono<String> applyUseCase(String playlistId) {
        return this.getPlaylistById
                .getPlaylist(playlistId)
                .map(playlistDTO -> {
                    this.playlistRepository.delete(playlistMapper.convertDTOToEntity().apply(playlistDTO));
                    return playlistDTO.getIdPlaylist();
                });
    }
}
