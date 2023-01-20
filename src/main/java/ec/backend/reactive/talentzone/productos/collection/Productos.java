package ec.backend.reactive.talentzone.productos.collection;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "products")
public class Productos {
    @MongoId
//    private String idAlbum = UUID.randomUUID().toString().substring(0, 10);
    private String name;
    private Integer inInventory;
    private Boolean enabled;
    private Integer min;
    private Integer max;

    //private ArrayList<Song> songs=new ArrayList<>();

}
