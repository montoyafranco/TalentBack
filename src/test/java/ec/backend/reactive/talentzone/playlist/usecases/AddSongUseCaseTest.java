package ec.backend.reactive.talentzone.playlist.usecases;

import ec.backend.reactive.talentzone.song.dto.SongDTO;
import ec.backend.reactive.talentzone.playlist.dto.PlaylistDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalTime;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class AddSongUseCaseTest {
    @Mock
    GetPlaylistByIdUseCase useCaseGetById;

    @Mock
    UpdatePlaylistUseCase useCaseUpdate;

    AddSongUseCase useCase;

    @BeforeEach
    void init(){
        useCase=new AddSongUseCase(useCaseGetById,useCaseUpdate);
    }

    @Test
    @DisplayName("addSongToPlaylist()")
    void addSongToPlaylistUseCase(){
        var songToAdd = new SongDTO("12345-6","songTest","65432-1","lyricsTest","producerTest","arrangedTest", LocalTime.of(0,3,45));
        var songToAdd2 = new SongDTO("12345-6","songTest","65432-1","lyricsTest","producerTest","arrangedTest", LocalTime.of(0,1,00));
        var playlistFound = new PlaylistDTO("1234-5", "PlaylistTest","usernameTest",new ArrayList<>(), LocalTime.of(0,0,0));
        var playlistUpdated = playlistFound.toBuilder().songs(new ArrayList<>(){ {add(songToAdd); add(songToAdd2);}}).duration(LocalTime.of(0,4,45)).build();

        Mockito.when(useCaseGetById.getPlaylist(Mockito.any(String.class))).thenReturn(Mono.just(playlistFound));
        Mockito.when(useCaseUpdate.applyUseCase(Mockito.any(String.class),Mockito.any(PlaylistDTO.class))).thenReturn(Mono.just(playlistUpdated));

        var useCaseExecute = useCase.addToPlaylist(playlistFound.getIdPlaylist(), songToAdd);

        StepVerifier.create(useCaseExecute)
                .expectNext(playlistUpdated)
                .verifyComplete();

        Mockito.verify(useCaseGetById).getPlaylist(Mockito.any(String.class));
        Mockito.verify(useCaseUpdate).applyUseCase(Mockito.any(String.class),Mockito.any(PlaylistDTO.class));
    }

}