package tienda.main;

import tienda.catalogo.CargaCatalogo;
import tienda.catalogo.Producto;
import tienda.descuento.DiscountManager;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Carga tienda_catalogo.csv
        List<Producto> catalogo = CargaCatalogo.cargarPorDefecto();
        if (catalogo.isEmpty()) {
            System.out.println("Catálogo vacío.");
            return;
        }

        Scanner sc = new Scanner(System.in);

        // Muestra el catálogo enumerado
        System.out.println("=== Catálogo (elige un producto) ===");
        for (int i = 0; i < catalogo.size(); i++) {
            Producto pr = catalogo.get(i);
            System.out.printf("%2d) %s | %s | $%.0f | %s | %s%n",
                    (i + 1), pr.getSku(), pr.getNombre(), pr.getPrecio(),
                    pr.getGrupoEdad(), pr.getTemporada());
        }

        // Permite elegir por identificación (SKU)
        System.out.print("Ingresa número o SKU: ");
        String sel = sc.nextLine().trim();

        Producto elegido = null;
        if (sel.matches("\\d+")) {
            int idx = Integer.parseInt(sel) - 1;
            if (idx >= 0 && idx < catalogo.size()) {
                elegido = catalogo.get(idx);
            }
        } else {
            String skuBuscado = sel.toUpperCase(Locale.ROOT);
            for (Producto pr : catalogo) {
                if (pr.getSku().equalsIgnoreCase(skuBuscado)) {
                    elegido = pr;
                    break;
                }
            }
        }

        if (elegido == null) {
            System.out.println("Selección inválida.");
            return;
        }

        String codigo = resolverCodigoPorProducto(elegido);

        // Obtenemos la instancia única (Singleton)
        DiscountManager dm = DiscountManager.getInstance();

        // Calculamos el precio final
        double precioOriginal = elegido.getPrecio();
        double precioFinal = (codigo == null || codigo.isBlank())
                ? precioOriginal
                : dm.applyDiscount(precioOriginal, codigo);

        String detalleCodigo;
        if (codigo == null || codigo.isBlank()) {
            detalleCodigo = "(sin descuento)";
        } else {
            Double pct = dm.getPromotionPercent(codigo);
            detalleCodigo = (pct != null)
                    ? String.format(Locale.ROOT, "%s → %.0f%%", codigo, pct * 100.0)
                    : codigo + " → 0% (no registrado)";
        }

        // Resumen boleta
        System.out.println("\n=== Detalle de compra ===");
        System.out.println("Producto: " + elegido.getSku() + " - " + elegido.getNombre());
        System.out.printf("Precio original: $%.0f%n", precioOriginal);
        System.out.println("Código aplicado: " + detalleCodigo);
        System.out.printf("Precio final:    $%.0f%n", precioFinal);
    }

    // Lógica para decidir el código
    private static String resolverCodigoPorProducto(Producto p) {
        String grupo = p.getGrupoEdad().toLowerCase(Locale.ROOT).trim();
        if ("nino".equals(grupo)) {
            return "KIDS12";
        }
        String temp = p.getTemporada().toLowerCase(Locale.ROOT).trim();
        switch (temp) {
            case "primavera": return "PRIMAVERA10";
            case "verano":    return "VERANO15";
            case "otoño":     return "OTOÑO12";
            case "invierno":  return "INVIERNO10";
            default:          return null;
        }
    }
}
