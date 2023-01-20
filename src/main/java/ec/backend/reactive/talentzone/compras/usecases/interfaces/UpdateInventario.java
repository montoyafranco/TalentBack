package ec.backend.reactive.talentzone.compras.usecases.interfaces;

import ec.backend.reactive.talentzone.productos.dto.ProductosDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface UpdateInventario {
    Mono<ProductosDTO> applyUpdateProductos(String id, ProductosDTO productosDTO);
}
