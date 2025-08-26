package tienda.main;

import tienda.catalogo.CargaCatalogo;
import tienda.catalogo.Producto;
import tienda.descuento.DiscountManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Carga tienda_catalogo.csv
        List<Producto> catalogo = CargaCatalogo.cargarPorDefecto();
        if (catalogo.isEmpty()) {
            System.out.println("Catálogo vacío.");
            return;
        }

        // Instancia única para descuentos (Singleton)
        DiscountManager dm = DiscountManager.getInstance();

        // Controlador del menú
        ControladorMenu controlador = new ControladorMenu(catalogo, dm);
        controlador.iniciar();
    }
}
