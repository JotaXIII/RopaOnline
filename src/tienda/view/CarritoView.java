package tienda.view;

import tienda.model.Carrito;
import tienda.model.Producto;
import tienda.util.MenuConsola;

public class CarritoView {

    // Muestra carrito y total
    public void imprimirCarrito(Carrito carrito) {
        System.out.println("=== Carrito ===");
        if (carrito == null || carrito.getLineas().isEmpty()) {
            System.out.println("(vacío)");
            return;
        }
        for (int i = 0; i < carrito.getLineas().size(); i++) {
            Producto p = carrito.getLineas().get(i);
            double precioLinea = carrito.getPrecioLinea(i);
            System.out.printf(" %d) %s | %s | $%.0f%n", i + 1, p.getSku(), p.getNombre(), precioLinea);
        }
        System.out.printf("Total: $%.0f%n", carrito.getTotal());
    }

    // Elige línea por número
    public int elegirLineaIndex1(Carrito carrito) {
        imprimirCarrito(carrito);
        return MenuConsola.elegirOpcion(carrito == null ? 0 : carrito.getLineas().size(), "Opción: ");
    }

    public void imprimirMensaje(String msg) {
        if (msg == null || msg.isBlank()) return;
        System.out.println(msg);
    }
}
