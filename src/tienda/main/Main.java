package tienda.main;

import tienda.catalogo.CargaCatalogo;
import tienda.model.Producto;
import tienda.model.Carrito;
import tienda.model.Usuario;
import tienda.model.DiscountManager;
import tienda.view.CatalogoView;
import tienda.view.CarritoView;
import tienda.view.DescuentoView;
import tienda.view.PedidoView;
import tienda.controller.ProductoController;
import tienda.controller.CarritoController;
import tienda.controller.DescuentoController;
import tienda.controller.PedidoController;

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

        // Modelos base
        Carrito carrito = new Carrito();
        Usuario usuario = new Usuario("anon");

        // Vistas
        CatalogoView catalogoView = new CatalogoView();
        CarritoView carritoView = new CarritoView();
        DescuentoView descuentoView = new DescuentoView();
        PedidoView pedidoView = new PedidoView();

        // Controladores
        ProductoController productoController = new ProductoController(catalogo, catalogoView);
        CarritoController carritoController = new CarritoController(carrito, carritoView);
        DescuentoController descuentoController = new DescuentoController(dm, descuentoView);
        PedidoController pedidoController = new PedidoController(carrito, usuario, dm, pedidoView);

        // Controlador del menú
        ControladorMenu controlador = new ControladorMenu(
                productoController,
                carritoController,
                descuentoController,
                pedidoController
        );
        controlador.iniciar();
    }
}
