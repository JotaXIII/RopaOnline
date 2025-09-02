package tienda.view;

import tienda.model.Carrito;
import tienda.model.Pedido;
import tienda.model.Producto;

public class PedidoView {

    // Muestra un resumen previo a confirmar
    public void imprimirResumenCarrito(Carrito carrito, double subtotal, double descuentos, double total) {
        System.out.println("=== Resumen del pedido (preview) ===");
        if (carrito == null || carrito.getLineas().isEmpty()) {
            System.out.println("(carrito vacío)");
            return;
        }

        for (int i = 0; i < carrito.getLineas().size(); i++) {
            Producto p = carrito.getLineas().get(i);
            double base = p.getPrecio();
            double actual = carrito.getPrecioLinea(i); // precio después del descuento
            int n = i + 1;
            System.out.printf(" %d) %s | %s | base:$%.0f | actual:$%.0f%n",
                    n, p.getSku(), p.getNombre(), base, actual);
        }

        System.out.printf("Subtotal:  $%.0f%n", subtotal);
        System.out.printf("Descuentos:$%.0f%n", descuentos);
        System.out.printf("Total:     $%.0f%n", total);
    }

    // Muestra el pedido ya confirmado
    public void imprimirPedidoConfirmado(Pedido pedido) {
        System.out.println("=== Pedido confirmado ===");
        if (pedido == null || pedido.getLineas().isEmpty()) {
            System.out.println("(sin líneas)");
            return;
        }

        System.out.printf("ID: %s%n", pedido.getId());
        System.out.printf("Fecha: %s%n", pedido.getFecha());
        System.out.printf("Usuario: %s%n", pedido.getUsuario().getNombre());
        System.out.printf("Estado: %s%n", pedido.getEstado());

        System.out.println("--- Líneas ---");
        for (Pedido.LineaPedido lp : pedido.getLineas()) {
            System.out.printf(" - %s | %s | base:$%.0f | final:$%.0f%n",
                    lp.getSku(), lp.getNombre(), lp.getPrecioBase(), lp.getPrecioFinal());
        }

        System.out.println("--- Totales ---");
        System.out.printf("Subtotal:  $%.0f%n", pedido.getSubtotal());
        System.out.printf("Descuentos:$%.0f%n", pedido.getDescuentos());
        System.out.printf("Total:     $%.0f%n", pedido.getTotal());
    }

    public void imprimirMensaje(String msg) {
        if (msg == null || msg.isBlank()) return;
        System.out.println(msg);
    }
}
