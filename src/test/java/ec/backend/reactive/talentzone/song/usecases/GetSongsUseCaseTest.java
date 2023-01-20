package ec.backend.reactive.talentzone.song.usecases;

import ec.backend.reactive.talentzone.song.dto.SongDTO;
import ec.backend.reactive.talentzone.song.mapper.SongMapper;
import ec.backend.reactive.talentzone.song.repositories.ISongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Objects;

@ExtendWith(MockitoExtension.class)
class GetSongsUseCaseTest {
    @Mock
    ISongRepository songRepositoryMock;
    SongMapper songMapper;
    GetSongsUseCase useCase;


    @BeforeEach
    void init(){
        songMapper = new SongMapper(new ModelMapper());
        useCase = new GetSongsUseCase(songRepositoryMock,songMapper);

    }

    @Test
    void getAllSongs() {
        var fluxExpected = Flux.just(new SongDTO(),new SongDTO());

        Mockito.when(songRepositoryMock.findAll()).thenReturn(fluxExpected.map(songDTO -> songMapper.convertDTOToEntity().apply(songDTO)));

        var useCaseExecute = useCase.getAllSongs();

        StepVerifier.create(useCaseExecute)
                .expectNextMatches(Objects::nonNull)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(songRepositoryMock).findAll();
    }
}