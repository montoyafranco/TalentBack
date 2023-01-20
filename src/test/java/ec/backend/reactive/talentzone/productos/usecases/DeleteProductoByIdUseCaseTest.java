package ec.backend.reactive.talentzone.productos.usecases;

import ec.backend.reactive.talentzone.playlist.collection.Playlist;
import ec.backend.reactive.talentzone.playlist.dto.PlaylistDTO;
import ec.backend.reactive.talentzone.playlist.mapper.PlaylistMapper;
import ec.backend.reactive.talentzone.productos.repository.IProductosRepository;
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
class DeleteProductoByIdUseCaseTest {
//    @Mock
//    GetProductosByIdUseCase useCaseFindByIdMock;
//
//    @Mock
//    SaveProductoUseCase saveProductoUseCaseMock;
//
//    UpdateProductosUseCase useCaseUpdate;
//
//    @BeforeEach
//    void init(){
//
//        useCase = new DeleteProductoByIdUseCase(useCaseGetById, iProductosRepository, modelMapper);
//
//
//    }
//
//    @Test
//    @DisplayName("deleteById()")
//    void deletePlaylistByIdUseCase(){
//        var playlistFound = new PlaylistDTO("1234-5", "PlaylistTest","usernameTest",new ArrayList<>(), LocalTime.of(0,0,0));
//
//        Mockito.when(useCaseGetById.getPlaylist(Mockito.any(String.class))).thenReturn(Mono.just(playlistFound));
//        Mockito.when(iProductosRepository.delete(modelMapper.convertDTOToEntity().apply(playlistFound))).thenReturn(Mono.empty());
//
//        var useCaseExecute = useCase.applyUseCase(playlistFound.getIdPlaylist());
//
//        StepVerifier.create(useCaseExecute)
//                .expectNext(playlistFound.getIdPlaylist())
//                .verifyComplete();
//
//        Mockito.verify(useCaseGetById).getPlaylist(Mockito.any(String.class));
//        Mockito.verify(iProductosRepository).delete(Mockito.any(Playlist.class));
//
//    }
//
}