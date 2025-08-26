package tienda.command;

import tienda.catalogo.Producto;
import tienda.decorator.Component;
import tienda.decorator.ConcreteComponentProducto;
import tienda.decorator.DecoratorDescuentoFijo10;
import tienda.decorator.DecoratorDescuentoPorCategoria;
import tienda.decorator.PrecioContexto;

// Concrete Command:
// Aplica la cadena de decoradores a un producto y lo agrega al carrito
// dejando en la línea el precio ya descontado.
public class CommandAplicarDescuentosLinea implements Command {

    private final Carrito carrito;
    private final Producto producto;

    public CommandAplicarDescuentosLinea(Carrito carrito, Producto producto) {
        this.carrito = carrito;
        this.producto = producto;
    }

    @Override
    public void ejecutar() {
        // 1) Calcula el precio con Decorator usando un contexto mutable
        Component comp = new ConcreteComponentProducto(producto);
        comp = new DecoratorDescuentoPorCategoria(comp, producto.getCategoria(), 0.20); // -20% si coincide
        comp = new DecoratorDescuentoFijo10(comp);                                      // -10% global

        PrecioContexto ctx = new PrecioContexto(producto);
        comp.operacion(ctx); // aplica descuentos acumulados sobre ctx.precio

        // 2) Agrega la línea al carrito (receiver)
        carrito.producto = producto;
        carrito.modo = "APLICAR_LINEA";
        carrito.accion();

        // 3) Reemplaza el precio de esa línea por el precio con descuento
        int index1 = carrito.getLineas().size();  // 1-based del último agregado
        carrito.fijarPrecioLinea(index1, ctx.precio);

        // Nota: el cálculo del total se deja a cargo de CommandCalcularTotalCarrito.
        // Si se requiere refrescar de inmediato, encola ese comando desde el invoker llamante.
    }
}
