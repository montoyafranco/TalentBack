package ec.backend.reactive.talentzone.compras.mapper;

import ec.backend.reactive.talentzone.compras.dto.ComprasDTO;
import ec.backend.reactive.talentzone.compras.collection.Compras;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
@AllArgsConstructor
public class ComprasMapper {
    private final ModelMapper modelMapper;

    public Function<Compras, ComprasDTO> convertEntityToDTO(){
        return compras -> modelMapper.map(compras, ComprasDTO.class);
    }
    public Function<ComprasDTO,Compras> convertDTOTOEntity(){
        return comprasDTO ->
                modelMapper.map(comprasDTO,Compras.class);
    }
}
