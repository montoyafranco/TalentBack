
# Reto DevJunior Web Java



Se requiere una solución Backend para el manejo de inventario y la compra de dichos productos disponibles.

Crear Apis que permitan listar, agregar, editar y eliminar productos al inventario. El listado de productos debe contar con paginación y las acciones que editan la base de datos sus respectivas validaciones.



Crear Apis de compras que permiten registrar la compra, la cual debe validar respectivamente la disponibilidad, los mínimos, máximos por producto, asentar la compra y descontar las cantidades compradas también debemos contar con un api para ver el historial de compras realizadas.

## Base de Datos mongodb


Correr en Mongo db con tu propia base de datos o mia . Se crea automaticamente con las peticiones POST de las colecciones Productos o Compras

```bash
  spring.data.mongodb.uri=mongodb+srv://agus:1234@cluster0.2nw1k6u.mongodb.net/Cluster0?retryWrites=true&w=majority
```
    
## Features

- Springboot Reactivo con Webflux y funcionalidad 
- Testing Mockito Test unitarios
- Se utilizo Casos de uso 
- Division en capas  
- DTO - colecciones - mapper -repositorio - router -usecases

