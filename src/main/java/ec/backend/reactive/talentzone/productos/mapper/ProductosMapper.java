package ec.backend.reactive.talentzone.productos.mapper;

import ec.backend.reactive.talentzone.productos.collection.Productos;
import ec.backend.reactive.talentzone.productos.dto.ProductosDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.function.Function;


/*
* This class-level annotation treats it as a custom bean.
* In other words, without having to write any explicit code, Spring will:
* 1. Scan our application for classes annotated with @Component.
* 2. Instantiate them and inject any specified dependencies into them.
* */
@Component
@AllArgsConstructor
public class ProductosMapper {

    private final ModelMapper modelMapper;

    public Function<Productos, ProductosDTO> convertEntityToDTO(){
        return productos ->
                modelMapper.map(productos, ProductosDTO.class);
    }

    public  Function<ProductosDTO, Productos> convertDTOToEntity (){
        return productoDTO ->
                modelMapper.map(productoDTO, Productos.class);
    }
}
