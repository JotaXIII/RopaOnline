package tienda.view;

import tienda.model.Carrito;
import tienda.util.MenuConsola;

import java.util.ArrayList;
import java.util.List;

public class DescuentoView {

    // Elige línea por número
    public int elegirLineaIndex1(Carrito carrito, CarritoView carritoView) {
        carritoView.imprimirCarrito(carrito);
        return MenuConsola.elegirOpcion(carrito == null ? 0 : carrito.getLineas().size(), "Opción (línea): ");
    }

    // Muestra códigos disponibles
    public String elegirCodigoDesdeLista(List<String> codigos) {
        System.out.println("=== Códigos de promoción ===");
        System.out.println(" 0) Ninguno");
        List<String> lista = codigos == null ? new ArrayList<>() : codigos;
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf(" %d) %s%n", i + 1, lista.get(i));
        }
        int op = MenuConsola.leerEntero("Opción (código): ");
        if (op == 0) return "";
        if (op >= 1 && op <= lista.size()) return lista.get(op - 1);
        return null;
    }

    public void imprimirExitoAplicacion(String referencia, double precioAntes, double precioDespues) {
        System.out.printf("Descuento aplicado a %s. Antes: $%.0f | Ahora: $%.0f%n",
                (referencia == null || referencia.isBlank()) ? "línea" : referencia,
                precioAntes, precioDespues);
    }

    public void imprimirMensaje(String msg) {
        if (msg == null || msg.isBlank()) return;
        System.out.println(msg);
    }
}
