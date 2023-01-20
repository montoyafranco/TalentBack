package ec.backend.reactive.talentzone.productos.usecases;

import ec.backend.reactive.talentzone.productos.dto.ProductosDTO;
import ec.backend.reactive.talentzone.productos.mapper.ProductosMapper;
import ec.backend.reactive.talentzone.productos.repository.IProductosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;
/*Remember our Functional Interface Function? Well, Function always receive something and returns another,
 * so this is the expected behavior that we need in this usecase. Also remember that to activate a Function
 * you use the apply() method. So, when you do the implementation you will override that method and set up inside what
 * do you want to truly implement.*/
@Service
@RequiredArgsConstructor
public class GetProductosByIdUseCase implements Function<String, Mono<ProductosDTO>>{

    //Don't forget our default initializations: ProductosMapper and IProductosRepository
    //Why we are not using @Autowired?
    private final IProductosRepository productosRepository;

    private final ProductosMapper productosMapper;

    /*From now we will treat the Mono.error() on the respective route in ProductosRouter. So, let focus only in
    * launch then alongside with the switchIfEmpty()*/
    @Override
    public Mono<ProductosDTO> apply(String name) {
        return productosRepository
                .findById(name)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(productos -> productosMapper.convertEntityToDTO().apply(productos));
    }
}
