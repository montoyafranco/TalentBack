package ec.backend.reactive.talentzone.productos.dto;

import lombok.*;

import java.util.Optional;
import java.util.function.Predicate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductosDTO {
    private String name;
    private Integer inInventory;
    private Boolean enabled;
    private Integer min;
    private Integer max;

    /*This predicate was created to verify if an attribute is not sent throught the postman's body, equally to said it is null
    * I made use of final class Optional and its static method .ofNullable() and .isEmpty() to ensure all */
    public static Predicate<ProductosDTO> thereIsNullAttributes(){
        return albumDTO -> Optional.ofNullable(albumDTO.getName()).isEmpty();
//                || Optional.ofNullable(albumDTO.getArtist()).isEmpty()
//                || Optional.ofNullable(albumDTO.getYearRelease()).isEmpty();
    }

}