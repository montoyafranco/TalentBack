package ec.backend.reactive.talentzone.productos.usecases;

import ec.backend.reactive.talentzone.productos.dto.ProductosDTO;
import ec.backend.reactive.talentzone.productos.mapper.ProductosMapper;
import ec.backend.reactive.talentzone.productos.repository.IProductosRepository;
import ec.backend.reactive.talentzone.productos.usecases.interfaces.SaveProducto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SaveProductoUseCase implements SaveProducto {

    private final IProductosRepository albumRepository;

    private final ProductosMapper productosMapper;

    /*I update it in order to manage scenarios when we sent an empty ProductosDTO object or some of the attributes is null
    * Using the ternary operator I split both cases. On the router, I will manage the catch of the exception dropped by
    * switchIfEmpty also the Mono.errors()*/
    @Override
    public Mono<ProductosDTO> applyProducto(ProductosDTO productosDTO) {
        return !Objects.isNull(productosDTO)? //A static method in final class Objects that allows to check if productosDTO is null
                !ProductosDTO.thereIsNullAttributes().test(productosDTO) ? //This is a predicate, I create it on ProductosDTO class
                this.albumRepository
                        .save(productosMapper.convertDTOToEntity().apply(productosDTO))
                        .switchIfEmpty(Mono.error(new Throwable(HttpStatus.EXPECTATION_FAILED.toString())))
                        .map(album -> productosMapper.convertEntityToDTO().apply(album))
                : Mono.error(new Throwable(HttpStatus.NOT_ACCEPTABLE.toString()))
        : Mono.error(new Throwable(HttpStatus.NOT_ACCEPTABLE.toString()));

    }



}
