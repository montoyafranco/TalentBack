package ec.backend.reactive.talentzone.compras.router;

import ec.backend.reactive.talentzone.compras.dto.ComprasDTO;
import ec.backend.reactive.talentzone.compras.usecases.GetHistorialUseCase;
import ec.backend.reactive.talentzone.compras.usecases.SaveComprasUseCase;
import ec.backend.reactive.talentzone.compras.usecases.interfaces.UpdateInventario;
import ec.backend.reactive.talentzone.productos.dto.ProductosDTO;
import ec.backend.reactive.talentzone.productos.repository.IProductosRepository;
import ec.backend.reactive.talentzone.productos.usecases.GetProductosByIdUseCase;
import ec.backend.reactive.talentzone.productos.usecases.GetProductsUseCase;
import ec.backend.reactive.talentzone.productos.usecases.UpdateProductosUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class ComprasRouter {
    private final IProductosRepository iProductosRepository;

    public ComprasRouter(IProductosRepository iProductosRepository) {
        this.iProductosRepository = iProductosRepository;
    }
 //AYUDAAA aca quiero re hacer el routerfunction gigante

    @Bean
    public RouterFunction<ServerResponse> saveComprasRouter(SaveComprasUseCase saveComprasUseCase) {
        return route(POST("/compras/save").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ComprasDTO.class).flatMap(
                        comprasDTO -> saveComprasUseCase.applyCompra(comprasDTO)
                                .collect(Collectors.toList())
                                .flatMap(
                                        result -> ServerResponse.status(HttpStatus.CREATED)
                                                .contentType(MediaType.APPLICATION_JSON).bodyValue(result)).onErrorResume(
                                        throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build())));
    }
    //este es el de como ya viene de uso de productos
    @Bean
    public RouterFunction<ServerResponse> UpdateStock(UpdateProductosUseCase updateProductosUseCase){
        return route(PUT("/productos/update/{name}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ProductosDTO.class) // aca tengo el json de postman
                        .flatMap(productosDTO -> updateProductosUseCase.applyUpdateProductos
                                        (request.pathVariable("name"),productosDTO)
                                .flatMap(result -> ServerResponse.status(HttpStatus.ACCEPTED)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable ->  ServerResponse.status(HttpStatus.NOT_MODIFIED)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .build())));
    }

    @Bean
    public RouterFunction<ServerResponse> UpdateStock2(UpdateInventario updateInventarioUseCase){
        return route(PUT("/productos/update/{name}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ProductosDTO.class) // aca tengo el json de postman productos
                        .flatMap(productosDTO -> updateInventarioUseCase.applyUpdateProductos
                                        (request.pathVariable("name"),productosDTO)
                                .flatMap(result -> ServerResponse.status(HttpStatus.ACCEPTED)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable ->  ServerResponse.status(HttpStatus.NOT_MODIFIED)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .build())));
    }
    @Bean //<-- A bean is a method-level annotation which simply a Java object which is created by Spring framework
    public RouterFunction<ServerResponse> getHistorial(GetHistorialUseCase getHistorialUseCase){
        return route(GET("/historial/all"), //Define the endpoint to be consumed
                //Keep in mind that you will retrieve a flux of ProductosDTO's
                //This .ok() does not mean that the usecase was already consumed
                request -> ServerResponse.ok()
                        //The mediatype is always json because json is yay
                        .contentType(MediaType.APPLICATION_JSON)
                        //Here we are going to call the usecase in order to set up the whole server response answer
                        //BodyInserts.fromPublisher? Remember that Flux is a publisher so, It makes sense the use of that method to get the Flux returned on the usecase
                        //You have to specified which type you are going to return inside the Flux<?>
                        .body(BodyInserters.fromPublisher(getHistorialUseCase.get(), ComprasDTO.class))
                        //If there is nothing, it falls here with my answer of NO_CONTENT
                        .onErrorResume(throwable ->  ServerResponse.status(HttpStatus.NO_CONTENT).build())

        );
    }
}
