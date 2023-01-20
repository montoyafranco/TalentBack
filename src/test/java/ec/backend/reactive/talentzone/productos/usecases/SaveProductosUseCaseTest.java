package ec.backend.reactive.talentzone.productos.usecases;

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
class SaveProductosUseCaseTest {
    @Mock
    IProductosRepository iProductosRepository;
    SaveProductoUseCase useCase;
    ProductosMapper productosMapper;

    @BeforeEach
    void init(){
        productosMapper = new ProductosMapper(new ModelMapper());
        useCase = new SaveProductoUseCase(iProductosRepository, productosMapper);

    }

    @Test
    @DisplayName("saveProductosUseCase()")
    void SaveProductosUseCaseTest(){
        ProductosDTO productoEsperado = new ProductosDTO();
        productoEsperado.setMax(5);
        productoEsperado.setName("agua");
        productoEsperado.setEnabled(true);
        productoEsperado.setMin(2);
        productoEsperado.setInInventory(5);


        var productoAGuardar = productoEsperado.toBuilder().name("agua").build();
        Mockito.when(iProductosRepository
                .save(productosMapper.convertDTOToEntity().apply(productoAGuardar)))
                .thenReturn(Mono.just(productosMapper.convertDTOToEntity().apply(productoEsperado)));

        var useCaseExecute = useCase.applyProducto(productoAGuardar);
        StepVerifier.create(useCaseExecute)
                .expectNext(productoEsperado)
                .expectComplete()
                .verify();
        Mockito.verify(iProductosRepository).save(productosMapper.convertDTOToEntity().apply(productoAGuardar));
    }
}