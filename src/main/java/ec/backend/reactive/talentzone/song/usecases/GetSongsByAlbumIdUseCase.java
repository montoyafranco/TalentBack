package ec.backend.reactive.talentzone.song.usecases;

import ec.backend.reactive.talentzone.productos.usecases.GetProductosByIdUseCase;
import ec.backend.reactive.talentzone.song.dto.SongDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetSongsByAlbumIdUseCase {
    public final GetProductosByIdUseCase getProductosByIdUseCase;
    public final GetSongsUseCase getSongsUseCase;

    public Flux<SongDTO> byAlbumId(String albumId){
        return getProductosByIdUseCase.apply(albumId)
                .flatMapMany(albumDTO -> getSongsUseCase
                        .getAllSongs()
                        .filter(songDTO -> songDTO.getIdAlbum().equals(albumDTO.getName())));
    }
}
