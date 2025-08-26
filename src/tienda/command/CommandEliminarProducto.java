package tienda.command;

// Concrete Command: elimina una línea del carrito por índice
public class CommandEliminarProducto implements Command {

    private final Carrito carrito;
    private final int indice1;

    public CommandEliminarProducto(Carrito carrito, int indice1) {
        this.carrito = carrito;
        this.indice1 = indice1;
    }

    @Override
    public void ejecutar() {
        carrito.indiceEliminar = indice1;
        carrito.skuEliminar = null;
        carrito.modo = "ELIMINAR_LINEA";
        carrito.accion();
    }
}
