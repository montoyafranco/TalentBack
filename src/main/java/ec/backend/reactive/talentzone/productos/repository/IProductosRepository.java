package ec.backend.reactive.talentzone.productos.repository;

import ec.backend.reactive.talentzone.productos.collection.Productos;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface IProductosRepository extends ReactiveMongoRepository<Productos, String> {

  @Query("{ 'name' : ?0 }")
  Flux<Productos> findByName(String name);
}
