package ec.backend.reactive.talentzone.song.usecases;

import ec.backend.reactive.talentzone.song.dto.SongDTO;
import ec.backend.reactive.talentzone.song.mapper.SongMapper;
import ec.backend.reactive.talentzone.song.repositories.ISongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalTime;

@ExtendWith(MockitoExtension.class)
class GetSongByIdUseCaseTest {
    @Mock
    ISongRepository songRepositoryMock;

    SongMapper songMapper;

    GetSongByIdUseCase useCase;

    @BeforeEach
    void init(){
        songMapper = new SongMapper(new ModelMapper());
        useCase = new GetSongByIdUseCase(songRepositoryMock,songMapper);
    }

    @Test
    @DisplayName("getSongById()")
    void getSongById() {
        SongDTO songDTOFound = new SongDTO("12345-6",
                "songTest",
                "65432-1",
                "lyricsTest",
                "producerTest",
                "arrangedTest",
                LocalTime.of(0,3,45));

        var songFound = songMapper.convertDTOToEntity().apply(songDTOFound);

        Mockito.when(songRepositoryMock.findById(Mockito.any(String.class))).thenReturn(Mono.just(songFound));

        var useCaseExecute = useCase.getSongById(songDTOFound.getIdSong());

        StepVerifier.create(useCaseExecute)
                .expectNext(songDTOFound)
                .verifyComplete();

        Mockito.verify(songRepositoryMock).findById(Mockito.any(String.class));
    }
}