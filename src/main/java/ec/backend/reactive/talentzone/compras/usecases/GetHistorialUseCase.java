package ec.backend.reactive.talentzone.compras.usecases;

import ec.backend.reactive.talentzone.compras.dto.ComprasDTO;
import ec.backend.reactive.talentzone.compras.mapper.ComprasMapper;
import ec.backend.reactive.talentzone.compras.repository.IComprasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;
/*
A Supplier interface is used to return an object or objects requested without sending any parameters because it is not needed
So, in this use case we will call all the possible registers located in Productos collection.
Do you need parameters to acomplish that? Nope.
So, Supplier is useful here.
* */
@Service
@RequiredArgsConstructor
public class GetHistorialUseCase implements Supplier<Flux<ComprasDTO>> {

    private final IComprasRepository iComprasRepository;

    private final ComprasMapper comprasMapper;
    /*When u implement Supplier you always have to override the get() method, then you will call in on the router for the
     * consumption.  In this get() also I'm treating the case where there is nothing in the collection. The management of the Httpstatus it is done
     * on the router
     *
     * */
    @Override
    public Flux<ComprasDTO> get() {
        return this.iComprasRepository
                .findAll()
                .switchIfEmpty(Flux.error(new Throwable(HttpStatus.NO_CONTENT.toString()))) //If there is nothing, switch to nothing dude and go to onErrorResume
                .map(compras -> comprasMapper.convertEntityToDTO().apply(compras)); //Otherwise return the Flux of DTO's albums
        //.onErrorResume(throwable -> Flux.empty()); //Return nothing when drops on switch
    }
}
