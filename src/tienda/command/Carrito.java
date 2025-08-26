package tienda.command;

import tienda.catalogo.Producto;

import java.util.ArrayList;
import java.util.List;

public class Carrito {

    // Receiver del patrón Command
    // Mantiene líneas y su precio actual

    private final List<Producto> lineas = new ArrayList<>();
    private final List<Double> precios = new ArrayList<>();

    private double total = 0.0;

    // Contexto usado por los comandos
    String modo = "";
    Producto producto = null;
    Integer indiceEliminar = null;
    String skuEliminar = null;

    public void accion() {
        if ("APLICAR_LINEA".equals(modo)) {
            if (producto != null) {
                lineas.add(producto);
                precios.add(producto.getPrecio()); // inicia con precio base
            }
            producto = null;
            modo = "";
            return;
        }

        if ("ELIMINAR_LINEA".equals(modo)) {
            if (indiceEliminar != null) {
                int idx = indiceEliminar - 1;
                if (idx >= 0 && idx < lineas.size()) {
                    lineas.remove(idx);
                    precios.remove(idx);
                }
            } else if (skuEliminar != null) {
                for (int i = 0; i < lineas.size(); i++) {
                    if (lineas.get(i).getSku().equalsIgnoreCase(skuEliminar)) {
                        lineas.remove(i);
                        precios.remove(i);
                        break;
                    }
                }
            }
            indiceEliminar = null;
            skuEliminar = null;
            modo = "";
            return;
        }

        if ("CALCULAR_TOTAL".equals(modo)) {
            double suma = 0.0;
            for (double v : precios) {
                suma += v; // suma precio actual de cada línea
            }
            total = suma;
            modo = "";
        }
    }

    // Setter para actualizar el precio actual
    public void fijarPrecioLinea(int index1, double nuevoPrecio) {
        int idx = index1 - 1;
        if (idx >= 0 && idx < precios.size()) {
            precios.set(idx, nuevoPrecio);
        }
    }

    // Getters
    public List<Producto> getLineas() { return lineas; }
    public double getTotal() { return total; }

    // Precio actual
    public double getPrecioLinea(int index0) {
        return (index0 >= 0 && index0 < precios.size()) ? precios.get(index0) : 0.0;
    }
}
