package tienda.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Catalogo {

    // Lista base de productos cargados desde CSV
    private final List<Producto> productos;

    public Catalogo(List<Producto> productos) {
        // copia para no depender de la lista externa
        this.productos = productos == null ? new ArrayList<>() : new ArrayList<>(productos);
    }

    // tamaño del catálogo
    public int size() { return productos.size(); }

    // está vacío
    public boolean isEmpty() { return productos.isEmpty(); }

    // devuelve la lista
    public List<Producto> getProductos() { return Collections.unmodifiableList(productos); }

    // obtiene producto por índice
    public Producto getPorIndice1(int index1) {
        int idx = index1 - 1;
        if (idx < 0 || idx >= productos.size()) return null;
        return productos.get(idx);
    }

    // busca por SKU
    public Producto buscarPorSku(String sku) {
        if (sku == null) return null;
        String s = sku.trim();
        if (s.isEmpty()) return null;
        for (Producto p : productos) {
            if (p.getSku().equalsIgnoreCase(s)) {
                return p;
            }
        }
        return null;
    }

    // filtra por categoría
    public List<Producto> filtrarPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) return getProductos();
        String c = categoria.trim().toLowerCase();
        List<Producto> out = new ArrayList<>();
        for (Producto p : productos) {
            String pc = p.getCategoria() == null ? "" : p.getCategoria().trim().toLowerCase();
            if (pc.equals(c)) out.add(p);
        }
        return out;
    }
}
