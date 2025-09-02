package tienda.controller;

import tienda.model.Carrito;
import tienda.model.Pedido;
import tienda.model.Producto;
import tienda.model.Usuario;
import tienda.model.DiscountManager;
import tienda.view.PedidoView;
import tienda.command.Invoker;
import tienda.command.CommandCalcularTotalCarrito;

public class PedidoController {

    private final Carrito carrito;
    private final Usuario usuario;
    private final DiscountManager dm;
    private final PedidoView view;


    private final Invoker invoker = new Invoker();

    public PedidoController(Carrito carrito, Usuario usuario, DiscountManager dm, PedidoView view) {
        this.carrito = carrito;
        this.usuario = usuario;
        this.dm = dm;
        this.view = view;
    }

    // muestra un resumen del pedido usando el carrito actual
    public void interactuarVerResumen() {
        if (carrito.getLineas().isEmpty()) {
            view.imprimirMensaje("Carrito vacío.");
            return;
        }

        // refresca total del carrito
        invoker.agregarComando(new CommandCalcularTotalCarrito(carrito));
        invoker.ejecutarComandos();

        // calcula y descuentos
        double subtotal = 0.0;
        for (Producto p : carrito.getLineas()) {
            subtotal += p.getPrecio();
        }
        double total = carrito.getTotal(); // suma de precios actuales (post-descuento)
        double descuentos = Math.max(0.0, subtotal - total);

        // imprime resumen
        view.imprimirResumenCarrito(carrito, subtotal, descuentos, total);
    }

    // confirma el pedido y vacía el carrito
    public void interactuarConfirmarPedido() {
        if (carrito.getLineas().isEmpty()) {
            view.imprimirMensaje("Carrito vacío.");
            return;
        }

        // refresca total antes de confirmar
        invoker.agregarComando(new CommandCalcularTotalCarrito(carrito));
        invoker.ejecutarComandos();

        Pedido pedido = Pedido.crearConfirmado(carrito, usuario);

        // muestra detalle de confirmación
        view.imprimirPedidoConfirmado(pedido);

        // limpia el carrito
        carrito.vaciar();
    }
}
