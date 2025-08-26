package tienda.decorator;

import tienda.catalogo.Producto;

public class ConcreteComponentProducto implements Component {

    // Punto de partida: fija el precio base en el contexto.
    private final Producto producto;

    public ConcreteComponentProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public void operacion(PrecioContexto ctx) {
        // Asegura que parta del precio base del producto.
        ctx.precio = producto.getPrecio();
    }
}
