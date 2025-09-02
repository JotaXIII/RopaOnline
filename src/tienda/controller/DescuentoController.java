package tienda.controller;

import tienda.model.Carrito;
import tienda.model.Producto;
import tienda.model.DiscountManager;
import tienda.view.DescuentoView;
import tienda.view.CarritoView;
import tienda.command.Invoker;
import tienda.command.CommandCalcularTotalCarrito;
import java.util.List;

public class DescuentoController {

    private final DiscountManager dm;
    private final DescuentoView view;
    private final CarritoView carritoView = new CarritoView();
    private final Invoker invoker = new Invoker();

    public DescuentoController(DiscountManager dm, DescuentoView view) {
        this.dm = dm;
        this.view = view;
    }

    public void interactuarAplicarDescuentoALinea(CarritoController carritoController) {
        Carrito carrito = carritoController.getCarrito();
        if (carrito.getLineas().isEmpty()) {
            view.imprimirMensaje("Carrito vacío.");
            return;
        }

        int indice1 = view.elegirLineaIndex1(carrito, carritoView);
        if (indice1 < 1 || indice1 > carrito.getLineas().size()) {
            view.imprimirMensaje("Selección inválida.");
            return;
        }
        int idx0 = indice1 - 1;
        Producto producto = carrito.getLineas().get(idx0);
        double precioAntes = carrito.getPrecioLinea(idx0);

        List<String> codigos = dm.getPromotionCodesSorted();
        String codigo = view.elegirCodigoDesdeLista(codigos);
        if (codigo == null) {
            view.imprimirMensaje("Selección inválida.");
            return;
        }

        // calcular nuevo precio (decorator DiscountManager)
        double precioNuevo = dm.applyDecorators(producto, codigo);

        // fijar precio y recalcular total
        carrito.fijarPrecioLinea(indice1, precioNuevo);
        invoker.agregarComando(new CommandCalcularTotalCarrito(carrito));
        invoker.ejecutarComandos();

        view.imprimirExitoAplicacion(String.valueOf(indice1), precioAntes, precioNuevo);
    }
}
