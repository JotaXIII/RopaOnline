package tienda.command;

import tienda.model.Carrito;
import tienda.model.Producto;
import tienda.model.DiscountManager;

// Concrete Command aplica descuento
public class CommandAplicarDescuentoLineaCarrito implements Command {

    private final Carrito carrito;
    private final int indice1;
    private final DiscountManager dm;
    private final String codigo;

    public CommandAplicarDescuentoLineaCarrito(Carrito carrito, int indice1, DiscountManager dm, String codigo) {
        this.carrito = carrito;
        this.indice1 = indice1;
        this.dm = dm;
        this.codigo = codigo;
    }

    @Override
    public void ejecutar() {
        int idx0 = indice1 - 1;
        if (idx0 < 0 || idx0 >= carrito.getLineas().size()) return;

        Producto p = carrito.getLineas().get(idx0);

        // calcula precio final (decorators DiscountManager)
        double nuevoPrecio = dm.applyDecorators(p, codigo);

        // fija el precio en carrito
        carrito.fijarPrecioLinea(indice1, nuevoPrecio);
    }
}
