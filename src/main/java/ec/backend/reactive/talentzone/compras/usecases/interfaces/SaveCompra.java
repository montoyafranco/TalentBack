package ec.backend.reactive.talentzone.compras.usecases.interfaces;

import ec.backend.reactive.talentzone.compras.dto.ComprasDTO;
import reactor.core.publisher.Mono;
@FunctionalInterface
public interface SaveCompra {


        Mono<ComprasDTO> applyCompra(ComprasDTO comprasDTO);

}
