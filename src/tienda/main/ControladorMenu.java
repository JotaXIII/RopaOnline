package tienda.main;

import tienda.catalogo.Producto;
import tienda.descuento.DiscountManager;
import tienda.util.MenuConsola;

// Command
import tienda.command.Carrito;
import tienda.command.Invoker;
import tienda.command.CommandAplicarDescuentosLinea;          // agrega producto aplicando Decorator
import tienda.command.CommandEliminarProducto;               // elimina línea por índice
import tienda.command.CommandCalcularTotalCarrito;           // recalcula total
import tienda.command.CommandAplicarDescuentoLineaCarrito;   // aplica Decorator a línea del carrito

import java.util.List;
import java.util.Scanner;

public class ControladorMenu {


    private final List<Producto> catalogo;
    private final DiscountManager dm;
    private final Carrito carrito = new Carrito();
    private final Invoker invoker = new Invoker();
    private final Scanner sc = new Scanner(System.in);

    public ControladorMenu(List<Producto> catalogo, DiscountManager dm) {
        this.catalogo = catalogo;
        this.dm = dm;
    }

    // Bucle principal del menú
    public void iniciar() {
        boolean seguir = true;
        while (seguir) {
            // Catálogo + resumen siempre visibles
            MenuConsola.imprimirCatalogo(catalogo);
            MenuConsola.imprimirResumenCarrito(carrito); // lista todas las líneas con precio actual y el total
            MenuConsola.imprimirMenuPrincipal();

            int op = MenuConsola.leerOpcion(sc, 0, 3);
            switch (op) {
                case 1: agregarProducto(); break;
                case 2: eliminarProducto(); break;
                case 3: verCarritoYDescuentos(); break;
                case 0:
                    seguir = false;
                    System.out.println("Saliendo...");
                    break;
            }
        }
    }

    // Opción 1: agregar producto al carrito
    private void agregarProducto() {
        Producto seleccionado = MenuConsola.leerSeleccionProducto(sc, catalogo);
        if (seleccionado == null) {
            System.out.println("No se seleccionó producto.");
            return;
        }
        // Agrega la línea aplicando la cadena de Decorator y fijando el precio con descuento
        invoker.agregarComando(new CommandAplicarDescuentosLinea(carrito, seleccionado));
        invoker.agregarComando(new CommandCalcularTotalCarrito(carrito));
        invoker.ejecutarComandos();

        System.out.println("Producto agregado al carrito.");
    }

    // Opción 2: eliminar producto del carrito
    private void eliminarProducto() {
        if (carrito.getLineas().isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }
        MenuConsola.imprimirLineasCarrito(carrito);
        int indice = MenuConsola.leerSeleccionLineaCarrito(sc, carrito);
        if (indice <= 0 || indice > carrito.getLineas().size()) {
            System.out.println("Selección inválida.");
            return;
        }

        invoker.agregarComando(new CommandEliminarProducto(carrito, indice)); // índice 1-based
        invoker.agregarComando(new CommandCalcularTotalCarrito(carrito));     // refresca el total
        invoker.ejecutarComandos();

        System.out.println("Producto eliminado del carrito.");
    }

    // Opción 3: ver carrito
    private void verCarritoYDescuentos() {
        boolean volver = false;
        while (!volver) {

            MenuConsola.imprimirLineasCarrito(carrito);
            MenuConsola.imprimirSubmenuCarrito();

            int op = MenuConsola.leerOpcion(sc, 0, 2);
            switch (op) {
                case 1: aplicarDescuentosALinea(); break;
                case 2: calcularTotalConCommand(); break;
                case 0: volver = true; break;
            }
        }
    }

    // aplica Decorator sobre una línea del carrito mediante Command y actualiza el total
    private void aplicarDescuentosALinea() {
        if (carrito.getLineas().isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }
        MenuConsola.imprimirLineasCarrito(carrito);
        int indice = MenuConsola.leerSeleccionLineaCarrito(sc, carrito);
        if (indice <= 0 || indice > carrito.getLineas().size()) {
            System.out.println("Selección inválida.");
            return;
        }

        invoker.agregarComando(new CommandAplicarDescuentoLineaCarrito(carrito, indice));
        invoker.agregarComando(new CommandCalcularTotalCarrito(carrito)); // refresca el total
        invoker.ejecutarComandos();

        System.out.println("Descuento aplicado.");
    }

    // calcular total del carrito mediante Command
    private void calcularTotalConCommand() {
        invoker.agregarComando(new CommandCalcularTotalCarrito(carrito));
        invoker.ejecutarComandos();
        System.out.printf("Total del carrito: $%.0f%n", carrito.getTotal());
    }
}
