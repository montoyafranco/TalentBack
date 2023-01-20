package ec.backend.reactive.talentzone.song.usecases;

import ec.backend.reactive.talentzone.song.dto.SongDTO;
import ec.backend.reactive.talentzone.song.mapper.SongMapper;
import ec.backend.reactive.talentzone.song.repositories.ISongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

//Making the usecase with no Functional interface to show that it is not needed
@Service
@RequiredArgsConstructor
public class GetSongsUseCase {
    private final ISongRepository songRepository;
    private final SongMapper songMapper;

    public Flux<SongDTO> getAllSongs(){
        return this.songRepository
                .findAll()
                .switchIfEmpty(Flux.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                .map(song -> songMapper.convertEntityToDTO().apply(song));
    }

}
