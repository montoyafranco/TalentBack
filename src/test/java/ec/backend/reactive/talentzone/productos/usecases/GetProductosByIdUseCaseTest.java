package ec.backend.reactive.talentzone.productos.usecases;

import ec.backend.reactive.talentzone.productos.collection.Productos;
import ec.backend.reactive.talentzone.productos.dto.ProductosDTO;
import ec.backend.reactive.talentzone.productos.mapper.ProductosMapper;
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

@ExtendWith(MockitoExtension.class)
class GetProductosByIdUseCaseTest {

    @Mock
    IProductosRepository iProductosRepository;
    GetProductosByIdUseCase useCase;
    ProductosMapper productosMapper;

    @BeforeEach
    void init(){
        productosMapper = new ProductosMapper(new ModelMapper());
        useCase = new GetProductosByIdUseCase(iProductosRepository, productosMapper);

    }

    @Test
    @DisplayName("getProductosByIdUseCase()")
    void getAlbumByIdUseCaseTest(){
        Productos productosExpected = new Productos();

        productosExpected.setMax(5);
        productosExpected.setName("agua");
        productosExpected.setEnabled(true);
        productosExpected.setMin(2);
        productosExpected.setInInventory(5);

        var productosDTO = productosMapper.convertEntityToDTO().apply(productosExpected);

        Mockito.when(iProductosRepository.findById(Mockito.any(String.class))).thenReturn(Mono.just(productosExpected));

        var useCaseExecute = useCase.apply(productosExpected.getName());

        StepVerifier.create(useCaseExecute)
                .expectNext(productosDTO)
                .expectComplete()
                .verify();

        Mockito.verify(iProductosRepository).findById(productosExpected.getName());
    }



}