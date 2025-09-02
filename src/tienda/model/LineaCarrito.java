package tienda.model;

import java.util.Objects;

public class LineaCarrito {

    private final Producto producto;
    private int cantidad;
    private double precioActual;
    public LineaCarrito(Producto producto) {
        this(producto, 1, producto == null ? 0.0 : producto.getPrecio());
    }

    public LineaCarrito(Producto producto, int cantidad, double precioUnitario) {
        this.producto = Objects.requireNonNull(producto, "producto");
        this.cantidad = Math.max(1, cantidad);
        this.precioActual = Math.max(0.0, precioUnitario);
    }

    // getters simples
    public Producto getProducto() { return producto; }
    public String getSku() { return producto.getSku(); }
    public String getNombre() { return producto.getNombre(); }
    public int getCantidad() { return cantidad; }
    public double getPrecioActual() { return precioActual; }

    public double getSubtotal() { return precioActual * cantidad; }

    // actualiza cantidad
    public void setCantidad(int cantidad) {
        this.cantidad = Math.max(1, cantidad);
    }

    // fija el precio unitario actual
    public void setPrecioActual(double precio) {
        this.precioActual = Math.max(0.0, precio);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineaCarrito)) return false;
        LineaCarrito that = (LineaCarrito) o;
        return this.producto.getSku().equals(that.producto.getSku());
    }

    @Override
    public int hashCode() {
        return producto.getSku().hashCode();
    }
}
