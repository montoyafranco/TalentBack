package ec.backend.reactive.talentzone.productos.usecases;

import ec.backend.reactive.talentzone.productos.repository.IProductosRepository;
import ec.backend.reactive.talentzone.productos.usecases.interfaces.DeleteProducto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor

public class DeleteProductoByIdUseCase implements DeleteProducto {


    private final IProductosRepository productosRepository;

    private final GetProductosByIdUseCase productosByIdUseCase;

    @Override
    public Mono<String> applyUseCase(String name) {
        return productosByIdUseCase.apply(name)
                .map(productosDTO -> {
                    productosRepository.deleteById(productosDTO.getName()).subscribe();
                    System.out.println(productosDTO + productosDTO.getName());

                    return productosDTO.getName();
                });
    }


}
