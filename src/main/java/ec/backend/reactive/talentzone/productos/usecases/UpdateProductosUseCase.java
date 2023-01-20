package ec.backend.reactive.talentzone.productos.usecases;

import ec.backend.reactive.talentzone.productos.dto.ProductosDTO;
import ec.backend.reactive.talentzone.productos.usecases.interfaces.UpdateProductos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;


/*For update use case i create my own interface but it is possible to use a Function too as in GetProductosByIdUseCase
 * because you will send something and return another something*/
@Service
@RequiredArgsConstructor
public class UpdateProductosUseCase implements UpdateProductos {

    //private final IProductosRepository albumRepository;

    private final GetProductosByIdUseCase getProductosByIdUseCase;

    private final SaveProductoUseCase saveProductoUseCase;

    //private final ProductosMapper albumMapper;

    /*I'm considering again the scenarios when we sent an empty ProductosDTO object or some of the attributes is null
     * Using the ternary operator I split both cases. On the router, I will manage the catch of the exception dropped by
     * switchIfEmpty also the Mono.errors(). Remember: I will treat the Mono.error on the router. If I forgot it the usecase will fail*/
    @Override
    public Mono<ProductosDTO> applyUpdateProductos(String name, ProductosDTO productosDTO) {


        return getProductosByIdUseCase.apply(name)
                .flatMap(productosDTO1 -> {
//aca ver que onda como hacer que agarre el body
                    Objects.requireNonNull(productosDTO);
                    productosDTO1.setMax(productosDTO.getMax());
                    return saveProductoUseCase.applyProducto(productosDTO1);
                });

    }
}
