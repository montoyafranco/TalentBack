package ec.backend.reactive.talentzone.compras.Helpers;

import ec.backend.reactive.talentzone.compras.collection.ProductosCopia;
import ec.backend.reactive.talentzone.productos.collection.Productos;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Validations {

    public void existencias(List<Productos> listaDeProductosGuardados,
                            List<ProductosCopia> listaDeProductosAComprar) {

        listaDeProductosAComprar.forEach(productosCopia -> listaDeProductosGuardados.stream()
                .filter(productoGuardado -> productoGuardado.getName().equals(productosCopia.getName()))
                .findFirst().orElseThrow(
                        () -> new RuntimeException("No existe el producto " + productosCopia.getName())));
    }


    public void stockMinimo(List<Productos> listDeProductosGuardados) {
        listDeProductosGuardados.forEach(producto -> {
            if (producto.getInInventory() < producto.getMin()) {
                throw new RuntimeException(
                        "El producto " + producto.getName() + " no tiene el stock minimo para su venta");
            }
        });
    }

    public void rangoDeCantidad(List<Productos> listaDeProductosGuardados,
                                List<ProductosCopia> listaDeProductosAComprar) {
        listaDeProductosAComprar.forEach(productosCopia -> listaDeProductosGuardados.stream()
                .filter(productoGuardado -> productoGuardado.getName().equals(productosCopia.getName()))
                .findFirst().ifPresent(productoGuardado -> {
                    if (productosCopia.getQuantity() < productoGuardado.getMin()) {
                        throw new RuntimeException("La cantidad del producto " + productoGuardado.getName()
                                + " no es valida debe ser mayor a " + productoGuardado.getMin());
                    }

                    if (productosCopia.getQuantity() > productoGuardado.getMax()) {
                        throw new RuntimeException("La cantidad del producto " + productoGuardado.getName()
                                + " no es valida debe ser menor a " + productoGuardado.getMax());
                    }

                    if (productosCopia.getQuantity() > productoGuardado.getInInventory()) {
                        throw new RuntimeException(
                                "La cantidad que pretende comprar supera el stock actual del producto "
                                        + productoGuardado.getName() + " solo hay "
                                        + productoGuardado.getInInventory());
                    }
                }));
    }
}
