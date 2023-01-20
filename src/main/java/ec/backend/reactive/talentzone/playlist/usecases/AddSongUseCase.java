package ec.backend.reactive.talentzone.playlist.usecases;

import ec.backend.reactive.talentzone.playlist.dto.PlaylistDTO;
import ec.backend.reactive.talentzone.playlist.usecases.interfaces.AddSong;
import ec.backend.reactive.talentzone.playlist.usecases.interfaces.GetPlaylistById;
import ec.backend.reactive.talentzone.song.dto.SongDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AddSongUseCase implements AddSong {
    private final GetPlaylistById getPlaylistById;

    private final UpdatePlaylistUseCase updatePlaylistUseCase;

    @Override
    public Mono<PlaylistDTO> addToPlaylist(String playlistId, SongDTO songDTO) {
        return this.getPlaylistById
                .getPlaylist(playlistId)
                .flatMap(playlistDTO -> {
                    playlistDTO.getSongs().add(songDTO);
                    playlistDTO.setDuration(PlaylistDTO.addDuration(playlistDTO.getDuration(),songDTO.getDuration()));
                    return this.updatePlaylistUseCase.applyUseCase(playlistId,playlistDTO);
                });
    }


}
