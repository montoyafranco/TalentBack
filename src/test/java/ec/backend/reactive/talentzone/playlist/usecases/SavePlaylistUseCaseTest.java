package ec.backend.reactive.talentzone.playlist.usecases;

import ec.backend.reactive.talentzone.playlist.collection.Playlist;
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
class SavePlaylistUseCaseTest {
    @Mock
    IPlaylistRepository playlistRepositoryMock;

    PlaylistMapper playlistMapper;

    SavePlaylistUseCase useCase;

    @BeforeEach
    void init(){
        playlistMapper = new PlaylistMapper(new ModelMapper());
        useCase = new SavePlaylistUseCase(playlistRepositoryMock,playlistMapper);
    }

    @Test
    @DisplayName("savePlaylistTest()")
    void savePlaylistUseCase(){
        var playlistToSave = new PlaylistDTO(null, "PlaylistTest","usernameTest",null,null);
        var playlistSaved = playlistToSave.toBuilder().idPlaylist("1234-5").songs(new ArrayList<>()).duration(LocalTime.of(0,0,0)).build();

        Mockito.when(playlistRepositoryMock.save(Mockito.any(Playlist.class)))
                .thenReturn(Mono.just(playlistMapper.convertDTOToEntity().apply(playlistSaved)));

        var useCaseExecute = useCase.applyUseCase(playlistToSave);

        StepVerifier.create(useCaseExecute)
                .expectNext(playlistSaved)
                .verifyComplete();

        Mockito.verify(playlistRepositoryMock).save(Mockito.any(Playlist.class));
    }

}