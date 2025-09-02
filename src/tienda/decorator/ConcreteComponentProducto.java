package tienda.decorator;

import tienda.model.Producto;

public class ConcreteComponentProducto implements Component {

    // Punto de partida: fija el precio base
    private final Producto producto;

    public ConcreteComponentProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public void operacion(PrecioContexto ctx) {
        if (ctx == null) return;

        ctx.precio = producto.getPrecio();
    }
}
