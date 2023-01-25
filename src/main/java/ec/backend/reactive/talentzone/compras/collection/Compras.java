package ec.backend.reactive.talentzone.compras.collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "compras")
public class Compras {

//   private String idAlbum = UUID.randomUUID().toString().substring(0, 10);


    private Instant date = Instant.now();

    @Id
    private Integer id;
    private String idType;
    private String clientName;
    private ArrayList<ProductosCopia> products;

    public Instant getDate() {
        return date;
    }
    public void setDate(Instant date) {
        this.date = Instant.now();
    }




}
