package ec.backend.reactive.talentzone.playlist.usecases;

import ec.backend.reactive.talentzone.playlist.dto.PlaylistDTO;
import ec.backend.reactive.talentzone.playlist.mapper.PlaylistMapper;
import ec.backend.reactive.talentzone.playlist.repository.IPlaylistRepository;
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
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class GetPlaylistByIdUseCaseTest {
    @Mock
    IPlaylistRepository playlistRepositoryMock;
    PlaylistMapper playlistMapper;
    GetPlaylistByIdUseCase useCase;

    @BeforeEach
    void init(){
        playlistMapper = new PlaylistMapper(new ModelMapper());
        useCase = new GetPlaylistByIdUseCase(playlistRepositoryMock,playlistMapper);
    }

    @Test
    @DisplayName("getPlaylistById()")
    void getPlaylistByIdUseCase(){
        var playlistFound = new PlaylistDTO("12345-6", "PlaylistTest","usernameTest",new ArrayList<>(), LocalTime.of(0,0,0));

        Mockito.when(playlistRepositoryMock.findById(Mockito.any(String.class))).thenReturn(Mono.just(playlistMapper.convertDTOToEntity().apply(playlistFound)));

        var useCaseExecute = useCase.getPlaylist(playlistFound.getIdPlaylist());

        StepVerifier.create(useCaseExecute)
                .expectNext(playlistFound)
                .verifyComplete();

        Mockito.verify(playlistRepositoryMock).findById(Mockito.any(String.class));


    }

}