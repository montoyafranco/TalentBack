package ec.backend.reactive.talentzone.compras.usecases;

import ec.backend.reactive.talentzone.productos.dto.ProductosDTO;
import ec.backend.reactive.talentzone.productos.usecases.GetProductosByIdUseCase;
import ec.backend.reactive.talentzone.productos.usecases.SaveProductoUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;


/*For update use case i create my own interface but it is possible to use a Function too as in GetProductosByIdUseCase
 * because you will send something and return another something*/
@Service
@RequiredArgsConstructor
public class UpdateInventario implements ec.backend.reactive.talentzone.compras.usecases.interfaces.UpdateInventario {



    private final GetProductosByIdUseCase getProductosByIdUseCase;

    private final SaveProductoUseCase saveProductoUseCase;




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
