//package ec.com.reactive.music.compras.usecases;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.repository.Query;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.*;
//import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//
//@Configuration
//public class ejemplos {
//
//    private ReactiveMongoTemplate template;
//
//
//
//
//    @Bean
//    public RouterFunction<ServerResponse> createSales() {
//        return route(
//                POST("/create/").and(accept(MediaType.APPLICATION_JSON)),
//                request -> template.save(request.bodyToMono(Product.class), "Sales")
//                        .then(ServerResponse.ok().build())
//        );
//    }
//
//    public void handleCreateSale(ServerRequest request){
//        validarStock(request).doOnNext()
//    }
//
//
//    public Mono<Void> validarStock(ServerRequest request){
//        return request.bodyToMono(SalesModel.class).flatMap(s -> {
//            var productlist=s.getProducts();
//            var listaIDProductos=productlist.stream().map(Producto::getIdProduct
//            ).collect(Collectors.toList());
//            template.find(findProducts(listaIDProductos),Product.class,"Products")
//                    .collectList().doOnNext(p -> {
//                        if (p.size()!= productlist.size()){
//                            throw new RuntimeException(productlist.size()-p.size()+"de los productos no esta disponible");
//                        }
//                        productlist.forEach((pr)->pr.getCantidad());
//                        productlist.stream().findFirst();
//
//
//                    });
//            return Mono.empty();
//        });
//    }
//
//
//    private Query findProducts(List<Integer> ID) {
//        return new Query(Criteria.where("_id").in(ID));
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> History() {
//        return route(
//                GET("/history/").and(accept(MediaType.APPLICATION_JSON)),
//                request -> template.findAll((Product.class), "Sales").collectList()
//                        .flatMap(list -> ServerResponse.ok()
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .body(BodyInserters.fromPublisher(Flux.fromIterable(list), Product.class)))
//        );
//    }
//}
//
