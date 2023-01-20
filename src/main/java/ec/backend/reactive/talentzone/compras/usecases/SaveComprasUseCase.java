package ec.backend.reactive.talentzone.compras.usecases;

import ec.backend.reactive.talentzone.compras.Helpers.Validations;
import ec.backend.reactive.talentzone.compras.collection.ProductosCopia;
import ec.backend.reactive.talentzone.compras.dto.ComprasDTO;
import ec.backend.reactive.talentzone.compras.mapper.ComprasMapper;
import ec.backend.reactive.talentzone.compras.repository.IComprasRepository;
import ec.backend.reactive.talentzone.compras.usecases.interfaces.SaveCompra;
import ec.backend.reactive.talentzone.productos.collection.Productos;
import ec.backend.reactive.talentzone.productos.repository.IProductosRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SaveComprasUseCase implements SaveCompra {

    private final IComprasRepository iComprasRepository;
    private final IProductosRepository productRepository;
    private final ComprasMapper comprasMapper;
    private final Validations validations;


    @Override
    //Mono<List<Productos>> aca abajo cambie
    public Mono<ComprasDTO> applyCompra(ComprasDTO comprasDTO) {
        validaciones(comprasDTO);
        return !Objects.isNull(comprasDTO) ?
                !ComprasDTO.thereIsNullAttributes().test(comprasDTO) ?
                        this.iComprasRepository
                                .save(comprasMapper.convertDTOTOEntity().apply(comprasDTO))
                                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.EXPECTATION_FAILED.toString())))
                                .map(compras -> comprasMapper.convertEntityToDTO().apply(compras))
                        : Mono.error(new Throwable(HttpStatus.NOT_ACCEPTABLE.toString()))
                : Mono.error(new Throwable(HttpStatus.NOT_ACCEPTABLE.toString()));    }
    private void validaciones(ComprasDTO comprasDTO) {
        var productosAComprar = comprasDTO.getProducts();
        var idsDeProductosAComprar = productosAComprar.stream().map(ProductosCopia::getName)
                .collect(                        Collectors.toList());
        var productosGuardados = Flux.merge(getProductosGuardados(idsDeProductosAComprar))
                .collect(Collectors.toList());
        productosGuardados.flatMap(productos -> {            validations.existencias(productos, productosAComprar);
            validations.stockMinimo(productos);
            validations.rangoDeCantidad(productos, productosAComprar);
            return Mono.just(productos);
        });
    }
    private List<Flux<Productos>> getProductosGuardados(List<String> idsDeProductosAComprar) {
        return idsDeProductosAComprar.stream().map(productRepository::findByName)
                .collect( Collectors.toList());
    }
}
