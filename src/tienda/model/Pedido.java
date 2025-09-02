package tienda.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Pedido {

    // Estado simple del pedido
    public enum Estado { CREADO, CONFIRMADO, CANCELADO }
    // Identificador único
    private final String id;
    // Fecha/hora de creación
    private final LocalDateTime fecha;
    // Usuario dueño del pedido
    private final Usuario usuario;
    private final List<LineaPedido> lineas;
    // Totales
    private final double subtotal;
    private final double descuentos;
    private final double total;
    private final Estado estado;

    public Pedido(String id,
                  LocalDateTime fecha,
                  Usuario usuario,
                  List<LineaPedido> lineas,
                  double subtotal,
                  double descuentos,
                  double total,
                  Estado estado) {

        this.id = id == null || id.isBlank() ? UUID.randomUUID().toString() : id.trim();
        this.fecha = fecha == null ? LocalDateTime.now() : fecha;
        this.usuario = Objects.requireNonNull(usuario, "usuario");
        this.lineas = lineas == null ? new ArrayList<>() : new ArrayList<>(lineas);
        this.subtotal = Math.max(0.0, subtotal);
        this.descuentos = Math.max(0.0, descuentos);
        this.total = Math.max(0.0, total);
        this.estado = estado == null ? Estado.CREADO : estado;
    }

    // Crea un pedido desde el carrito
    public static Pedido desdeCarrito(Carrito carrito, Usuario usuario, Estado estado) {
        Objects.requireNonNull(carrito, "carrito");
        Objects.requireNonNull(usuario, "usuario");

        List<LineaPedido> ls = new ArrayList<>();
        double sub = 0.0;
        double tot = 0.0;

        List<Producto> productos = carrito.getLineas();
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            double base = p.getPrecio();
            double fin = carrito.getPrecioLinea(i);
            if (fin <= 0.0) fin = base;

            sub += base;
            tot += fin;

            ls.add(new LineaPedido(p, base, fin));
        }

        double desc = Math.max(0.0, sub - tot);
        return new Pedido(null, LocalDateTime.now(), usuario, ls, sub, desc, tot, estado);
    }

    public static Pedido crearConfirmado(Carrito carrito, Usuario usuario) {
        return desdeCarrito(carrito, usuario, Estado.CONFIRMADO);
    }

    // getters simples
    public String getId() { return id; }
    public LocalDateTime getFecha() { return fecha; }
    public Usuario getUsuario() { return usuario; }
    public List<LineaPedido> getLineas() { return Collections.unmodifiableList(lineas); }
    public double getSubtotal() { return subtotal; }
    public double getDescuentos() { return descuentos; }
    public double getTotal() { return total; }
    public Estado getEstado() { return estado; }

    // igualdad por id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;
        Pedido other = (Pedido) o;
        return id.equals(other.id);
    }
    @Override public int hashCode() { return id.hashCode(); }

    // Línea interna del pedido
    public static class LineaPedido {
        private final Producto producto;
        private final double precioBase;
        private final double precioFinal;

        public LineaPedido(Producto producto, double precioBase, double precioFinal) {
            this.producto = Objects.requireNonNull(producto, "producto");
            this.precioBase = Math.max(0.0, precioBase);
            this.precioFinal = Math.max(0.0, precioFinal);
        }

        public Producto getProducto() { return producto; }
        public String getSku() { return producto.getSku(); }
        public String getNombre() { return producto.getNombre(); }
        public double getPrecioBase() { return precioBase; }
        public double getPrecioFinal() { return precioFinal; }
    }
}
