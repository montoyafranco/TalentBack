package ec.backend.reactive.talentzone.compras.usecases;

import ec.backend.reactive.talentzone.productos.dto.ProductosDTO;
import ec.backend.reactive.talentzone.productos.usecases.GetProductosByIdUseCase;
import ec.backend.reactive.talentzone.productos.usecases.SaveProductoUseCase;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class UseCaseVENTAcompleta {
    // no se nadaaa wiiii iiii ii
    private final GetProductosByIdUseCase getProductosByIdUseCase;

    private final SaveProductoUseCase saveProductoUseCase;

    public UseCaseVENTAcompleta(GetProductosByIdUseCase getProductosByIdUseCase, SaveProductoUseCase saveProductoUseCase) {
        this.getProductosByIdUseCase = getProductosByIdUseCase;
        this.saveProductoUseCase = saveProductoUseCase;
    }

    public Mono<ProductosDTO> applyUpdateProductos(String name, ProductosDTO productosDTO) {

        return getProductosByIdUseCase.apply(name)
                .flatMap(productosDTO1 -> {

                    Objects.requireNonNull(productosDTO);

                    productosDTO1.setMax(productosDTO.getMax());
                    return saveProductoUseCase.applyProducto(productosDTO1);
                });

    }
}
