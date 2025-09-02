package tienda.decorator;

import tienda.model.Producto;

// Precio que cambia durante el cálculo, parte en el precio base
public class PrecioContexto {
    public final Producto producto;
    public double precio;

    public PrecioContexto(Producto producto) {
        this.producto = producto;
        this.precio = (producto == null) ? 0.0 : producto.getPrecio();
    }
}
