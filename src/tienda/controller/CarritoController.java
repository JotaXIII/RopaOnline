package tienda.controller;

import tienda.model.Carrito;
import tienda.model.Producto;
import tienda.view.CarritoView;
import tienda.command.Invoker;
import tienda.command.CommandAgregarProducto;
import tienda.command.CommandEliminarProducto;
import tienda.command.CommandCalcularTotalCarrito;

public class CarritoController {

    private final Carrito carrito;
    private final CarritoView view;
    private final Invoker invoker = new Invoker();

    public CarritoController(Carrito carrito, CarritoView view) {
        this.carrito = carrito;
        this.view = view;
    }

    public Carrito getCarrito() { return carrito; }

    public void agregarProducto(Producto p) {
        if (p == null) return;
        invoker.agregarComando(new CommandAgregarProducto(carrito, p));
        invoker.agregarComando(new CommandCalcularTotalCarrito(carrito));
        invoker.ejecutarComandos();
    }

    public void interactuarVerCarrito() {
        invoker.agregarComando(new CommandCalcularTotalCarrito(carrito));
        invoker.ejecutarComandos();
        view.imprimirCarrito(carrito);
    }

    public void interactuarEliminarProducto() {
        if (carrito.getLineas().isEmpty()) {
            view.imprimirMensaje("Carrito vacío.");
            return;
        }
        int index1 = view.elegirLineaIndex1(carrito);
        if (index1 < 1) {
            view.imprimirMensaje("Selección inválida.");
            return;
        }
        invoker.agregarComando(new CommandEliminarProducto(carrito, index1));
        invoker.agregarComando(new CommandCalcularTotalCarrito(carrito));
        invoker.ejecutarComandos();
        view.imprimirCarrito(carrito);
    }

    public double calcularTotal() {
        invoker.agregarComando(new CommandCalcularTotalCarrito(carrito));
        invoker.ejecutarComandos();
        return carrito.getTotal();
    }
}
