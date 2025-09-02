package tienda.controller;

import tienda.model.Catalogo;
import tienda.model.Producto;
import tienda.view.CatalogoView;
import java.util.List;

public class ProductoController {

    private final Catalogo catalogo;
    private final CatalogoView view;

    public ProductoController(List<Producto> productos, CatalogoView view) {
        this.catalogo = new Catalogo(productos);
        this.view = view;
    }

    public ProductoController(Catalogo catalogo, CatalogoView view) {
        this.catalogo = catalogo;
        this.view = view;
    }

    public void interactuarVerCatalogo() {
        view.imprimirCatalogo(catalogo.getProductos());
    }

    public void interactuarAgregarProductoAlCarrito(CarritoController carritoController) {
        if (catalogo.isEmpty()) {
            view.imprimirMensaje("Catálogo vacío.");
            return;
        }
        int index1 = view.elegirProductoIndex1(catalogo.getProductos());
        if (index1 < 1) {
            view.imprimirMensaje("Selección inválida.");
            return;
        }
        Producto elegido = catalogo.getPorIndice1(index1);
        if (elegido == null) {
            view.imprimirMensaje("Selección inválida.");
            return;
        }
        carritoController.agregarProducto(elegido);
        view.imprimirProductoAgregado(elegido);
    }
}
