package ec.backend.reactive.talentzone.productos.usecases.interfaces;

import ec.backend.reactive.talentzone.productos.dto.ProductosDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface UpdateProductos {
    Mono<ProductosDTO> applyUpdateProductos(String id, ProductosDTO productosDTO);
}
