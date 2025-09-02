package tienda.command;

import tienda.model.Carrito;
import tienda.model.Producto;

// Concrete Command agrega una línea al carrito.
public class CommandAgregarProducto implements Command {

    private final Carrito carrito;
    private final Producto producto;

    public CommandAgregarProducto(Carrito carrito, Producto producto) {
        this.carrito = carrito;
        this.producto = producto;
    }

    @Override
    public void ejecutar() {
        carrito.producto = producto;
        carrito.modo = "APLICAR_LINEA";
        carrito.accion();
    }
}
