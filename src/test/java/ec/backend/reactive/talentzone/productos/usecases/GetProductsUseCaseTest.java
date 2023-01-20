package ec.backend.reactive.talentzone.productos.usecases;

import ec.backend.reactive.talentzone.productos.collection.Productos;
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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Objects;

@ExtendWith(MockitoExtension.class)
class GetProductsUseCaseTest {
    @Mock
    IProductosRepository iProductosRepository;
    GetProductsUseCase useCase;
    ProductosMapper productosMapper;

    @BeforeEach
    void init(){
        productosMapper = new ProductosMapper(new ModelMapper());
        useCase = new GetProductsUseCase(iProductosRepository, productosMapper);

    }

    @Test
    @DisplayName("getProductsUseCase()")
    void getAlbumsUseCaseTest(){
        Flux<Productos> productosFlux = Flux.just(new Productos(),new Productos());

        Mockito.when(iProductosRepository.findAll()).thenReturn(productosFlux);

        var useCaseExecute = useCase.get();

        StepVerifier.create(useCaseExecute)
                .expectNextMatches(Objects::nonNull)
                        .expectNextCount(1).verifyComplete();

        Mockito.verify(iProductosRepository).findAll();

    }

}