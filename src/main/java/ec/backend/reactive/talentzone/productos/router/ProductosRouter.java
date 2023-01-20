package ec.backend.reactive.talentzone.productos.router;

import ec.backend.reactive.talentzone.productos.dto.ProductosDTO;
import ec.backend.reactive.talentzone.productos.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

//Why we need to use beans? Because we are doing specific/custom implementation
/*Why we need to use configuration? Because indicates that a class declares one or more @Bean methods and may be processed by the Spring container
to generate bean definitions and service requests for those beans at runtime.
In simple words, The @RestController annotation becames @Configuration for the functional approach implemented*/
@Configuration
public class ProductosRouter {
    @Bean //<-- A bean is a method-level annotation which simply a Java object which is created by Spring framework
    public RouterFunction<ServerResponse> getAlbumsRouter(GetProductsUseCase getProductsUseCase){
        return route(GET("/productos/all"), //Define the endpoint to be consumed
                //Keep in mind that you will retrieve a flux of ProductosDTO's
                //This .ok() does not mean that the usecase was already consumed
                request -> ServerResponse.ok()
                        //The mediatype is always json because json is yay
                        .contentType(MediaType.APPLICATION_JSON)
                        //Here we are going to call the usecase in order to set up the whole server response answer
                        //BodyInserts.fromPublisher? Remember that Flux is a publisher so, It makes sense the use of that method to get the Flux returned on the usecase
                        //You have to specified which type you are going to return inside the Flux<?>
                        .body(BodyInserters.fromPublisher(getProductsUseCase.get(), ProductosDTO.class))
                        //If there is nothing, it falls here with my answer of NO_CONTENT
                        .onErrorResume(throwable ->  ServerResponse.status(HttpStatus.NO_CONTENT).build())

        );
    }
    @Bean
    public RouterFunction<ServerResponse> deleteProductoRouter(DeleteProductoByIdUseCase deleteProductoByIdUseCase){
        return route(DELETE("/playlist/delete/{name}"),
                request -> deleteProductoByIdUseCase.applyUseCase(request.pathVariable("name"))
                        .flatMap(result -> ServerResponse.accepted()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }
    @Bean
    public RouterFunction<ServerResponse> getProductoById(GetProductosByIdUseCase getProductosByIdUseCase){
        return route(GET("/productos/{name}"),
                request -> getProductosByIdUseCase.apply(request.pathVariable("name"))
                        //If everything is ok, I will return a properly answer
                        .flatMap(albumDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(albumDTO))
                        //Error always propagates and it will fall here no matter what level there is
                        .onErrorResume(throwable ->  ServerResponse.notFound().build()));
        /*return route(GET("/productos/{id}"),
                request -> ServerResponse
                        .status(HttpStatus.FOUND)
                        .body(getProductosByIdUseCase.apply(request.pathVariable("id")), ProductosDTO.class)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build())
        );*/
    }

    /*UPDATE: Error handling here*/
    @Bean
    public RouterFunction<ServerResponse> saveProductoRouter(SaveProductoUseCase saveProductoUseCase){
        return route(
                POST("/productos/save").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ProductosDTO.class)
                    .flatMap(albumDTO -> saveProductoUseCase
                            .applyProducto(albumDTO)
                        .flatMap(result -> ServerResponse.status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(result))
                            //Error always propagates and it will fall here no matter what level there is
                            //Handle the exception that I dropped on the Mono.errors() inside the usecase
                        .onErrorResume(throwable ->  ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build())
        ));
    }



    @Bean
    public RouterFunction<ServerResponse> updateProducto(UpdateProductosUseCase updateProductosUseCase){
        return route(PUT("/productos/update/{name}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ProductosDTO.class)
                        .flatMap(productosDTO -> updateProductosUseCase.applyUpdateProductos(request.pathVariable("name"),productosDTO)
                        .flatMap(result -> ServerResponse.status(HttpStatus.ACCEPTED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                        .onErrorResume(throwable ->  ServerResponse.status(HttpStatus.NOT_MODIFIED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .build())));
    }
}
