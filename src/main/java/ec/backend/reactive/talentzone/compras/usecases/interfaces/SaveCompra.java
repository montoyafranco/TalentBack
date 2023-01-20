package ec.backend.reactive.talentzone.compras.usecases.interfaces;

import ec.backend.reactive.talentzone.compras.dto.ComprasDTO;
import ec.backend.reactive.talentzone.productos.collection.Productos;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@FunctionalInterface
public interface SaveCompra {


        Flux<Productos> applyCompra(ComprasDTO comprasDTO);

}
