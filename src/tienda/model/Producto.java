package tienda.model;

import java.util.Map;
import java.util.Objects;

public class Producto {

    // Datos del CSV
    private final String sku;
    private final String nombre;
    private final String categoria;
    private final String subcategoria;
    private final String grupoEdad;
    private final String talla;
    private final String color;
    private final String temporada;
    private final double precio;

    public Producto(String sku, String nombre, String categoria, String subcategoria,
                    String grupoEdad, String talla, String color, String temporada, double precio) {
        this.sku = Objects.requireNonNull(sku, "sku");
        this.nombre = Objects.requireNonNull(nombre, "nombre");
        this.categoria = Objects.requireNonNull(categoria, "categoria");
        this.subcategoria = Objects.requireNonNull(subcategoria, "subcategoria");
        this.grupoEdad = Objects.requireNonNull(grupoEdad, "grupoEdad");
        this.talla = Objects.requireNonNull(talla, "talla");
        this.color = Objects.requireNonNull(color, "color");
        this.temporada = Objects.requireNonNull(temporada, "temporada");
        this.precio = precio;
    }

    // getters simples
    public String getSku() { return sku; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getSubcategoria() { return subcategoria; }
    public String getGrupoEdad() { return grupoEdad; }
    public String getTalla() { return talla; }
    public String getColor() { return color; }
    public String getTemporada() { return temporada; }
    public double getPrecio() { return precio; }

    // crea un producto desde un mapa
    public static Producto desdeMapa(Map<String, String> fila) {
        String sku = trimSafe(fila.get("sku"));
        String nombre = trimSafe(fila.get("nombre"));
        String categoria = trimSafe(fila.get("categoria"));
        String subcategoria = trimSafe(fila.get("subcategoria"));
        String grupoEdad = trimSafe(fila.get("grupo_edad"));
        String talla = trimSafe(fila.get("talla"));
        String color = trimSafe(fila.get("color"));
        String temporada = trimSafe(fila.get("temporada"));

        double precio = 0.0;
        String precioStr = trimSafe(fila.get("precio"));
        if (!precioStr.isEmpty()) {
            try { precio = Double.parseDouble(precioStr); }
            catch (NumberFormatException e) { precio = 0.0; }
        }
        return new Producto(sku, nombre, categoria, subcategoria, grupoEdad, talla, color, temporada, precio);
    }

    private static String trimSafe(String v) { return v == null ? "" : v.trim(); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        return sku.equals(((Producto) o).sku);
    }
    @Override public int hashCode() { return sku.hashCode(); }
}
