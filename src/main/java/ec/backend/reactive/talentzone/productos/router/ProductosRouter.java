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


@Configuration
public class ProductosRouter {
    @Bean
    public RouterFunction<ServerResponse> getAlbumsRouter(GetProductsUseCase getProductsUseCase){
        return route(GET("/productos/all"),

                request -> ServerResponse.ok()

                        .contentType(MediaType.APPLICATION_JSON)

                        .body(BodyInserters.fromPublisher(getProductsUseCase.get(), ProductosDTO.class))

                        .onErrorResume(throwable ->  ServerResponse.status(HttpStatus.NO_CONTENT).build())

        );
    }
    @Bean
    public RouterFunction<ServerResponse> deleteProductoRouter(DeleteProductoByIdUseCase deleteProductoByIdUseCase){
        return route(DELETE("/producto/delete/{name}"),
                request -> deleteProductoByIdUseCase.applyUseCase(request.pathVariable("name"))
                        .flatMap(result ->
                                ServerResponse.accepted()
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

                        .onErrorResume(throwable ->  ServerResponse.notFound().build()));

    }


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
