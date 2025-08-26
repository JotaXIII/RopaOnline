package tienda.util;

import tienda.catalogo.Producto;
import tienda.command.Carrito;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public final class MenuConsola {

    private MenuConsola() { }

    // Imprime el catálogo enumerado
    public static void imprimirCatalogo(List<Producto> catalogo) {
        System.out.println("=== Catálogo ===");
        for (int i = 0; i < catalogo.size(); i++) {
            Producto pr = catalogo.get(i);
            System.out.printf("%2d) %s | %s | $%.0f | %s | %s%n",
                    (i + 1), pr.getSku(), pr.getNombre(), pr.getPrecio(),
                    pr.getGrupoEdad(), pr.getTemporada());
        }
    }

    // Resumen del carrito
    public static void imprimirResumenCarrito(Carrito carrito) {
        System.out.println("--- Resumen del carrito ---");
        System.out.println("Líneas: " + carrito.getLineas().size());
        for (int i = 0; i < carrito.getLineas().size(); i++) {
            Producto p = carrito.getLineas().get(i);
            double actual = carrito.getPrecioLinea(i);
            System.out.printf(" - %s | %s | $%.0f%n", p.getSku(), p.getNombre(), actual);
        }
        System.out.printf("Total: $%.0f%n", carrito.getTotal());
    }

    // Menú principal
    public static void imprimirMenuPrincipal() {
        System.out.println();
        System.out.println("=== Menú Principal ===");
        System.out.println("1) Agregar producto al carrito");
        System.out.println("2) Eliminar producto del carrito");
        System.out.println("3) Ver carrito");
        System.out.println("0) Salir");
        System.out.print("Opción: ");
    }

    // Submenú carrito
    public static void imprimirSubmenuCarrito() {
        System.out.println();
        System.out.println("=== Carrito: Descuentos ===");
        System.out.println("1) Aplicar descuentos");
        System.out.println("2) Calcular total del carrito");
        System.out.println("0) Volver");
        System.out.print("Opción: ");
    }

    // selección numérica
    public static int leerOpcion(Scanner sc, int min, int max) {
        while (true) {
            String s = sc.nextLine().trim();
            if (s.matches("\\d+")) {
                int op = Integer.parseInt(s);
                if (op >= min && op <= max) {
                    return op;
                }
            }
            System.out.print("Opción inválida. Intenta nuevamente: ");
        }
    }

    // Selección de producto por número o SKU
    public static Producto leerSeleccionProducto(Scanner sc, List<Producto> catalogo) {
        System.out.print("Ingresa número o SKU: ");
        String sel = sc.nextLine().trim();

        if (sel.matches("\\d+")) {
            int idx = Integer.parseInt(sel) - 1;
            if (idx >= 0 && idx < catalogo.size()) {
                return catalogo.get(idx);
            }
        } else {
            String skuBuscado = sel.toUpperCase(Locale.ROOT);
            for (Producto pr : catalogo) {
                if (pr.getSku().equalsIgnoreCase(skuBuscado)) {
                    return pr;
                }
            }
        }
        return null;
    }

    // Imprime carrito
    public static void imprimirLineasCarrito(Carrito carrito) {
        System.out.println("=== Carrito ===");
        if (carrito.getLineas().isEmpty()) {
            System.out.println("(vacío)");
            return;
        }
        for (int i = 0; i < carrito.getLineas().size(); i++) {
            Producto p = carrito.getLineas().get(i);
            double actual = carrito.getPrecioLinea(i);
            System.out.printf("%2d) %s | %s | $%.0f%n",
                    (i + 1), p.getSku(), p.getNombre(), actual);
        }
    }

    // Selección de línea por número o SKU
    public static int leerSeleccionLineaCarrito(Scanner sc, Carrito carrito) {
        System.out.print("Ingresa número o SKU del producto: ");
        String sel = sc.nextLine().trim();

        if (sel.matches("\\d+")) {
            return Integer.parseInt(sel);
        } else {
            String skuBuscado = sel.toUpperCase(Locale.ROOT);
            for (int i = 0; i < carrito.getLineas().size(); i++) {
                if (carrito.getLineas().get(i).getSku().equalsIgnoreCase(skuBuscado)) {
                    return i + 1; // 1-based
                }
            }
        }
        return -1;
    }
}
