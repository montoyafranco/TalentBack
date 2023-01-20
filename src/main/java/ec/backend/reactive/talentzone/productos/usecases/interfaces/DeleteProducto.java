package ec.backend.reactive.talentzone.productos.usecases.interfaces;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface DeleteProducto {


    Mono<String> applyUseCase(String name);
}
