package tienda.command;

import tienda.catalogo.Producto;
import tienda.decorator.Component;
import tienda.decorator.ConcreteComponentProducto;
import tienda.decorator.DecoratorDescuentoFijo10;
import tienda.decorator.DecoratorDescuentoPorCategoria;
import tienda.decorator.PrecioContexto;

// Concrete Command: aplica Decorator a una línea del carrito y actualiza el precio
public class CommandAplicarDescuentoLineaCarrito implements Command {

    private final Carrito carrito;
    private final int indice1;

    public CommandAplicarDescuentoLineaCarrito(Carrito carrito, int indice1) {
        this.carrito = carrito;
        this.indice1 = indice1;
    }

    @Override
    public void ejecutar() {
        int idx = indice1 - 1;
        if (idx < 0 || idx >= carrito.getLineas().size()) {
            return;
        }
        Producto p = carrito.getLineas().get(idx);

        // Cadena de decoradores - precio
        Component comp = new ConcreteComponentProducto(p);
        comp = new DecoratorDescuentoPorCategoria(comp, p.getCategoria(), 0.20);
        comp = new DecoratorDescuentoFijo10(comp);

        PrecioContexto ctx = new PrecioContexto(p);
        comp.operacion(ctx);

        // Actualiza el precio
        carrito.fijarPrecioLinea(indice1, ctx.precio);
    }
}
