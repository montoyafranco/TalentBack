package ec.backend.reactive.talentzone.compras.repository;

import ec.backend.reactive.talentzone.compras.collection.Compras;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IComprasRepository extends ReactiveMongoRepository<Compras,Integer> {
}
