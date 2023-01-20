package ec.backend.reactive.talentzone.song.mapper;

import ec.backend.reactive.talentzone.song.collections.Song;
import ec.backend.reactive.talentzone.song.dto.SongDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@AllArgsConstructor
public class SongMapper {

    private final ModelMapper modelMapper;

    public Function<Song, SongDTO> convertEntityToDTO(){
        return song -> modelMapper.map(song, SongDTO.class);
    }

    public Function<SongDTO,Song> convertDTOToEntity(){
        return songDTO -> modelMapper.map(songDTO, Song.class);
    }
}
