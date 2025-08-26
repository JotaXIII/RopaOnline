package tienda.decorator;

import tienda.catalogo.Producto;

// Contexto mutable de precio: inicia con el precio base

public class PrecioContexto {
    public final Producto producto;
    public double precio;

    public PrecioContexto(Producto producto) {
        this.producto = producto;
        this.precio = producto.getPrecio();
    }
}
