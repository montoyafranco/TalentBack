package ec.backend.reactive.talentzone.compras.dto;

import ec.backend.reactive.talentzone.compras.collection.ProductosCopia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ComprasDTO {

//   private String idAlbum = UUID.randomUUID().toString().substring(0, 10);


    private LocalDate date;

    @Id
    private Integer id;
    private String idType;
    private String clientName;
    private ArrayList<ProductosCopia> products;

    public static Predicate<ComprasDTO> thereIsNullAttributes(){
        return comprasDTO -> Optional.ofNullable(comprasDTO.getClientName()).isEmpty();}



}
