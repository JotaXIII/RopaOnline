package tienda.view;

import tienda.model.Producto;
import tienda.util.MenuConsola;

import java.util.List;

public class CatalogoView {

    // Imprime catálogo enumerado
    public void imprimirCatalogo(List<Producto> catalogo) {
        System.out.println("=== Catálogo ===");
        if (catalogo == null || catalogo.isEmpty()) {
            System.out.println("(sin productos)");
            return;
        }
        for (int i = 0; i < catalogo.size(); i++) {
            Producto p = catalogo.get(i);
            System.out.printf(" %d) %s | %s | $%.0f%n", i + 1, p.getSku(), p.getNombre(), p.getPrecio());
        }
    }

    // Elige producto por número
    public int elegirProductoIndex1(List<Producto> catalogo) {
        imprimirCatalogo(catalogo);
        return MenuConsola.elegirOpcion(catalogo == null ? 0 : catalogo.size(), "Opción: ");
    }

    // Mostrar confirmación de agregado
    public void imprimirProductoAgregado(Producto p) {
        if (p == null) {
            System.out.println("No se agregó ningún producto.");
            return;
        }
        System.out.printf("Agregado: %s | %s | $%.0f%n",
                p.getSku(), p.getNombre(), p.getPrecio());
    }

    public void imprimirMensaje(String msg) {
        if (msg == null || msg.isBlank()) return;
        System.out.println(msg);
    }
}
