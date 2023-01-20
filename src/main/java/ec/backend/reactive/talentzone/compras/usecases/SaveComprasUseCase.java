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
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
    public Flux<Productos> applyCompra(ComprasDTO comprasDTO) {
        return productRepository.saveAll(validaciones(comprasDTO).flatMapMany(productos -> {
            var productosAComprar = comprasDTO.getProducts();

            var listaActualizada = actualizarInventario(productos, productosAComprar);

            return Flux.fromIterable(listaActualizada);
        }));


    }

    private List<Productos> actualizarInventario(List<Productos> listaDeProductosGuardados,
                                                 List<ProductosCopia> listaDeProductosAComprar) {
        return listaDeProductosAComprar.stream().map(productosCopia -> {
            var product = listaDeProductosGuardados.stream()
                    .filter(productoGuardado -> productoGuardado.getName().equals(productosCopia.getName()))
                    .findFirst().orElseThrow();

            product.setInInventory(product.getInInventory() - productosCopia.getQuantity());

            return product;
        }).collect(Collectors.toList());
    }

    private Mono<List<Productos>> validaciones(ComprasDTO comprasDTO) {
        var productosAComprar = comprasDTO.getProducts();

        var idsDeProductosAComprar = productosAComprar.stream().map(ProductosCopia::getName)
                .collect(Collectors.toList());

        var productosGuardados = Flux.merge(getProductosGuardados(idsDeProductosAComprar))
                .collect(Collectors.toList());

        return productosGuardados.flatMap(productos -> {
            validations.existencias(productos, productosAComprar);
            validations.stockMinimo(productos);
            validations.rangoDeCantidad(productos, productosAComprar);

            return Mono.just(productos);
        });
    }

    private List<Flux<Productos>> getProductosGuardados(List<String> idsDeProductosAComprar) {
        return idsDeProductosAComprar.stream().map(productRepository::findByName)
                .collect(Collectors.toList());
    }
}
