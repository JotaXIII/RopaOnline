package tienda.command;

// Concrete Command
// Calcula el total del carrito sumando los precios actuales.
public class CommandCalcularTotalCarrito implements Command {

    private final Carrito carrito;  // receiver

    public CommandCalcularTotalCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    @Override
    public void ejecutar() {
        carrito.modo = "CALCULAR_TOTAL";
        carrito.accion();
    }
}
