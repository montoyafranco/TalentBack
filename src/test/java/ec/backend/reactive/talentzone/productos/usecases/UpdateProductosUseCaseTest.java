package ec.backend.reactive.talentzone.productos.usecases;

import ec.backend.reactive.talentzone.productos.dto.ProductosDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UpdateProductosUseCaseTest {

    @Mock
    GetProductosByIdUseCase useCaseFindByIdMock;

    @Mock
    SaveProductoUseCase saveProductoUseCaseMock;

    UpdateProductosUseCase useCaseUpdate;


    @BeforeEach
    void init(){
        useCaseUpdate = new UpdateProductosUseCase(useCaseFindByIdMock, saveProductoUseCaseMock);
    }

    @Test
    @DisplayName("updateProductsUseCase()")
    void updateAlbumUseCase(){
        ProductosDTO productosDTO = new ProductosDTO();

        productosDTO.setMax(5);
        productosDTO.setName("agus");
        productosDTO.setEnabled(true);
        productosDTO.setInInventory(10);
        productosDTO.setMin(3);

        var productosDTO1 = productosDTO.toBuilder().max(4).build();

        Mockito.when(useCaseFindByIdMock.apply(Mockito.any(String.class))).thenReturn(Mono.just(productosDTO));
        Mockito.when(saveProductoUseCaseMock.applyProducto(Mockito.any(ProductosDTO.class))).thenReturn(Mono.just(productosDTO1));

        var useCaseExecute = useCaseUpdate.applyUpdateProductos("agus",productosDTO1);

        StepVerifier.create(useCaseExecute)
                .expectNext(productosDTO1)
                .expectComplete()
                .verify();


        Mockito.verify(useCaseFindByIdMock).apply("agus");
        Mockito.verify(saveProductoUseCaseMock).applyProducto(productosDTO1);
    }


}