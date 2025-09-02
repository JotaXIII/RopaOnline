package tienda.main;

import tienda.controller.ProductoController;
import tienda.controller.CarritoController;
import tienda.controller.DescuentoController;
import tienda.controller.PedidoController;
import tienda.util.MenuConsola;
import java.util.Scanner;

public class ControladorMenu {

    // controladores (MVC)
    private final ProductoController productoController;
    private final CarritoController carritoController;
    private final DescuentoController descuentoController;
    private final PedidoController pedidoController;

    // consola
    private final Scanner sc = new Scanner(System.in);

    public ControladorMenu(ProductoController productoController,
                           CarritoController carritoController,
                           DescuentoController descuentoController,
                           PedidoController pedidoController) {
        this.productoController = productoController;
        this.carritoController = carritoController;
        this.descuentoController = descuentoController;
        this.pedidoController = pedidoController;
    }

    // loop principal del menú
    public void iniciar() {
        boolean seguir = true;
        while (seguir) {
            System.out.println("=== Menú Principal ===");
            System.out.println("1) Catálogo");
            System.out.println("2) Carrito");
            System.out.println("3) Descuentos");
            System.out.println("4) Cerrar compra");
            System.out.println("0) Salir");
            System.out.print("Opción: ");

            int op = leerEnteroSeguro();
            switch (op) {
                case 1:
                    menuCatalogo();
                    break;
                case 2:
                    menuCarrito();
                    break;
                case 3:
                    menuDescuentos();
                    break;
                case 4:
                    menuCheckout();
                    break;
                case 0:
                    seguir = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
            System.out.println();
        }
        System.out.println("Hasta luego.");
    }

    // submenú de catálogo
    private void menuCatalogo() {
        boolean volver = false;
        while (!volver) {
            System.out.println("=== Catálogo ===");
            System.out.println("1) Ver catálogo");
            System.out.println("2) Agregar producto al carrito");
            System.out.println("0) Volver");
            System.out.print("Opción: ");

            int op = leerEnteroSeguro();
            switch (op) {
                case 1:
                    // muestra productos
                    productoController.interactuarVerCatalogo();
                    break;
                case 2:
                    // agregar al carrito
                    productoController.interactuarAgregarProductoAlCarrito(carritoController);
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
            System.out.println();
        }
    }

    // submenú de carrito
    private void menuCarrito() {
        boolean volver = false;
        while (!volver) {
            System.out.println("=== Carrito ===");
            System.out.println("1) Ver carrito y total");
            System.out.println("2) Eliminar producto del carrito");
            System.out.println("0) Volver");
            System.out.print("Opción: ");

            int op = leerEnteroSeguro();
            switch (op) {
                case 1:
                    // muestra el carrito
                    carritoController.interactuarVerCarrito();
                    break;
                case 2:
                    // eliminar del carrito
                    carritoController.interactuarEliminarProducto();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
            System.out.println(); // espacio
        }
    }

    // submenú de descuentos
    private void menuDescuentos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("=== Descuentos ===");
            System.out.println("1) Aplicar descuento");
            System.out.println("0) Volver");
            System.out.print("Opción: ");

            int op = leerEnteroSeguro();
            switch (op) {
                case 1:
                    descuentoController.interactuarAplicarDescuentoALinea(carritoController);
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
            System.out.println();
        }
    }

    // submenú de checkout
    private void menuCheckout() {
        boolean volver = false;
        while (!volver) {
            System.out.println("=== Cerrar compra ===");
            System.out.println("1) Ver resumen del pedido");
            System.out.println("2) Confirmar pedido");
            System.out.println("0) Volver");
            System.out.print("Opción: ");

            int op = leerEnteroSeguro();
            switch (op) {
                case 1:
                    // muestra resumen
                    pedidoController.interactuarVerResumen();
                    break;
                case 2:
                    // valida, recalcula, mantiene precios y crea el pedido
                    pedidoController.interactuarConfirmarPedido();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
            System.out.println();
        }
    }

    private int leerEnteroSeguro() {
        String s = sc.nextLine().trim();
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
